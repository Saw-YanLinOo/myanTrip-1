package com.vmyan.myantrip.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.flipboard.bottomsheet.BottomSheetLayout
import com.flipboard.bottomsheet.commons.MenuSheetView
import com.orhanobut.hawk.Hawk
import com.vmyan.myantrip.R
import com.vmyan.myantrip.ui.adapter.MyAdapter
import com.vmyan.myantrip.ui.viewmodel.UploadViewModel
import com.vmyan.myantrip.utils.LoadingDialog
import com.vmyan.myantrip.utils.Resource
import com.vmyan.myantrip.utils.showToast
import kotlinx.android.synthetic.main.activity_post_upload.*
import kotlinx.android.synthetic.main.image_recycler_adapter.view.*
import org.koin.android.ext.android.inject

class PostUploadActivity : AppCompatActivity() {

    val CAMERA_FRAGMENT = 111
    val PICK_UP_IMAGE = 222
    val LOCATION = 333

    var description = ""
    var place_cat_id = ""
    var place_sub_id = ""
    var placeId = ""
    var imageListSize = 0
    var imageList = ArrayList<String>()

    private var loadingDiaglog = LoadingDialog(this)

    private lateinit var adapter : MyAdapter

    private val viewModel: UploadViewModel by inject()

    private lateinit var et_descriton : EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post_upload)

        val viewGroup = (findViewById<View>(android.R.id.content) as ViewGroup).getChildAt(0) as ViewGroup
        initUser(viewGroup)
        choosePostCategory(viewGroup)
    }

    private fun initUser(viewGroup: View) {
        var username = Hawk.get<String>("user_name")
        var userProfile = Hawk.get<String>("user_profile")

        user_name_upload.text = username
        Glide.with(applicationContext)
            .load(userProfile)
            .into(user_profile_upload)


        if (intent != null){
            var cat_id = intent!!.getStringExtra("place_cat_id")
            var sub_id = intent.getStringExtra("place_sub_id")
            var id = intent.getStringExtra("place_id")
            var name =intent.getStringExtra("place_name")
            var image =intent.getStringExtra("place_image")
            var category = intent.getStringExtra("place_category")
            var address = intent.getStringExtra("place_address")
            setUpPlace(cat_id!!,sub_id!!,id!!,name!!,image!!,category!!,address!!)
        }

        btn_Upload.setOnClickListener {
            et_descriton = findViewById<EditText>(R.id.et_description_upload)
            this.description = et_descriton.text.toString()
                checkType()
        }

        tv_place.setOnClickListener{
            setUpPlace("","","","","","","")
            layout_place.visibility = View.GONE

        }

        tv_photo.setOnClickListener {
            setUpImageRecycler(ArrayList<String>())
            photo_layout.visibility = View.GONE
        }
    }

    private fun uploadPost(description: String, imageList: ArrayList<String>, placeId: String,type : Long) {
        viewModel.uploadPhoto(imageList).observe(this, {
            when (it) {
                is Resource.Loading -> {
                    loadingDiaglog.startLoading()
                    println("loading.....uplaod Photo")
                }
                is Resource.Success -> {
                    var resultList = it.data.value!!
                    Log.e("Result Image List==>", resultList.toString())
                    viewModel.setPost(description, resultList,placeId,type).observe(this, {
                            when (it) {
                                is Resource.Loading -> {
                                    println("loading.....PostList")
                                }
                                is Resource.Success -> {
                                    loadingDiaglog.stopLoading()
                                    showToast("Upload Successful")
                                    finish()
                                }
                                is Resource.Failure -> {
                                    loadingDiaglog.stopLoading()
                                    println("Can't Upload post " + it.message)
                                }
                            }
                        })
                }
                is Resource.Failure -> {
                    loadingDiaglog.stopLoading()
                    println("Can't Upload Photo " + it.message)
                }
            }
        })
    }

    fun choosePostCategory(view: View) {
        val bottomSheet = findViewById<BottomSheetLayout>(R.id.post_cat_bottom_sheet)
        val menuSheetView = MenuSheetView(applicationContext,
            MenuSheetView.MenuType.LIST,
            "Create Your Post..."
        ) { item ->
            if (item.title.trim() == "Camera Capture"){
                var intent = Intent(applicationContext, SharePost::class.java)
                startActivityForResult(intent, CAMERA_FRAGMENT)
            }
            if (item.title.trim() == "Pick Up Images"){
                var intent = Intent(applicationContext, SharePost::class.java)
                startActivityForResult(intent, PICK_UP_IMAGE)
            }
            if (item.title.trim() == "Places"){
                var intent = Intent(applicationContext, SearchPlaceActivityForPost::class.java)
                startActivityForResult(intent, LOCATION)
            }
            if (bottomSheet.isSheetShowing) {
                bottomSheet.dismissSheet()
            }
            true
        }
        menuSheetView.inflateMenu(R.menu.upload_category)
        bottomSheet.showWithSheetView(menuSheetView)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && requestCode == CAMERA_FRAGMENT){
            println("Image ==> ${data!!.getStringArrayListExtra("imageList")}")
            if (data != null){
                println("Image ==> ${data!!.getStringArrayListExtra("imageList")}")
                setUpImageRecycler(data.getStringArrayListExtra("imageList")!!)
            }
        }
        if (resultCode == RESULT_OK && requestCode == PICK_UP_IMAGE){
            if (data != null){
                setUpImageRecycler(data.getStringArrayListExtra("imageList")!!)
            }
        }
        if (resultCode == RESULT_OK && requestCode == LOCATION){
            //showToast("Location ==> $data",Toast.LENGTH_SHORT)
            println("Location ==> $data")
            var cat_id = data!!.getStringExtra("place_cat_id")
            var sub_id = data.getStringExtra("place_sub_id")
            var id = data.getStringExtra("place_id")
            var name =data.getStringExtra("place_name")
            var image =data.getStringExtra("place_image")
            var category = data.getStringExtra("place_category")
            var address = data.getStringExtra("place_address")
            setUpPlace(cat_id!!,sub_id!!,id!!,name!!,image!!,category!!,address!!)
        }
    }

    private fun setUpPlace(cat_id : String,sub_id : String, id : String, name : String , image : String, category : String,address : String){
        layout_place.visibility = View.VISIBLE
        Glide.with(applicationContext)
            .load(image)
            .into(image_u)
        name_u.text = name
        address_u.text = address
        category_u.text = category

        this.place_cat_id = cat_id
        this.place_sub_id = sub_id
        this.placeId = id
        this.placeId = "${this.place_cat_id}/SubCategory/${this.place_sub_id}/Place/${this.placeId}"
    }

    private fun setUpImageRecycler(imageList: ArrayList<String>){
        println("ImageList ==> $imageList")
        photo_layout.visibility = View.VISIBLE
        adapter = MyAdapter(imageList)
        if (imageList.size == 1){
            multi_recyclerView.layoutManager = LinearLayoutManager(applicationContext,LinearLayoutManager.VERTICAL,false)
        }else{
            multi_recyclerView.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        }
        multi_recyclerView.adapter = adapter

        this.imageListSize = imageList.size
        this.imageList.addAll(imageList)
    }

    fun postType(description :String,imageList : Int, placeId : String) : String{
        if (imageList != 0 && placeId != ""){
            return "place and image"
        }else if(imageList != 0 && placeId == ""){
            return "image"
        }else if(imageList == 0 && placeId != ""){
            return "place"
        }else{
             return "description"
        }
    }

    private fun checkType() {
        var type = postType(this.description,this.imageListSize,this.placeId)
        if (type.trim() == "place and image"){
            uploadPost(this.description,this.imageList,this.placeId,4)
        }else if (type.trim() == "place"){
            uploadPost(this.description,this.imageList,this.placeId,3)
        }else if(type.trim() == "image"){
            uploadPost(this.description,this.imageList,this.placeId,2)
        }else {
            uploadPost(this.description,this.imageList,this.placeId,1)
        }
    }
}