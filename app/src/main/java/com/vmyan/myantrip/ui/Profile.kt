package com.vmyan.myantrip.ui

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory
import androidx.core.net.toUri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.BitmapImageViewTarget
import com.google.firebase.auth.FirebaseAuth
import com.orhanobut.hawk.Hawk
import com.vmyan.myantrip.R
import com.vmyan.myantrip.ui.adapter.PostListAdapter
import com.vmyan.myantrip.ui.viewmodel.ProfileViewModel
import com.vmyan.myantrip.utils.Resource
import kotlinx.android.synthetic.main.activity_profile.*
import kotlinx.android.synthetic.main.context_profile.*
import org.koin.android.ext.android.inject

class Profile : AppCompatActivity(),PostListAdapter.ItemClickListener {

    private val viewModel: ProfileViewModel by inject()

    val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private lateinit var postListAdapter: PostListAdapter
    private val REQUEST_CODE_READ_STORAGE = 2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        //Get user id from Incomming fragment and activity
        var userId = intent.getStringExtra("user_id")
        init(userId!!)
        btn_logout.setOnClickListener(View.OnClickListener {
            auth.signOut()
            Hawk.put("skip",false)
            val intent = Intent(this,Login::class.java)
            startActivity(intent)
            this.finishAffinity()
        })
        setUpProfilePostRecycler()
        setUpObserve(userId)

    }

    private fun init(userId: String) {

        if (userId.trim().toString() == auth.currentUser!!.uid){
            tv_username_p.text = Hawk.get<String>("user_name")
            loadPhoto(Hawk.get<String>("user_profile"))
        }

        fab_profile.setOnClickListener{
            if (userId.trim().toString() == auth.currentUser!!.uid){
                if (hasNoPermissions()) {
                    val permissions = arrayOf(android.Manifest.permission.CAMERA, android.Manifest.permission.WRITE_EXTERNAL_STORAGE, android.Manifest.permission.READ_EXTERNAL_STORAGE)
                    ActivityCompat.requestPermissions(this, permissions,0)
                }else{
                    showChooser()
                }
            }
        }
    }

    private fun showChooser() {
        val intent = Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        startActivityForResult(intent, REQUEST_CODE_READ_STORAGE);
    }

    private fun setUpProfilePostRecycler() {
        postListAdapter = PostListAdapter(this, mutableListOf())
        profile_recyclerView.layoutManager = LinearLayoutManager(applicationContext, LinearLayoutManager.VERTICAL,false)
        profile_recyclerView.apply {
            setHasFixedSize(true)
            setItemViewCacheSize(20)
        }
        profile_recyclerView.adapter = postListAdapter
    }

    @SuppressLint("showToast")
    private fun setUpObserve(userId : String) {
        viewModel.getUserProfile(userId).observe(this, Observer {
            when(it){
            is Resource.Loading -> {
                println("loading.....User Profile")
            }
            is Resource.Success -> {
                val result = it.data[0]
                println("name=="+result!!.user.username+"profile=="+result!!.user.profilephoto.toUri())
                val url = result!!.user.profilephoto
                tv_username_p.text = result!!.user.username
                loadPhoto(url)
                postListAdapter.setItems(it.data)
            }
            is Resource.Failure -> {
                Log.e("Failed fetch profile==>",it.message)
            }
        }
    })
    }

    override fun onPostClick(position: Int) {

    }

    private fun hasNoPermissions(): Boolean{
        return ContextCompat.checkSelfPermission(this,
            Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(this,
            Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(this,
            Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED
    }

    fun requestPermission(){
        ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA, android.Manifest.permission.WRITE_EXTERNAL_STORAGE, android.Manifest.permission.READ_EXTERNAL_STORAGE),0)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, resultData: Intent?) {
        super.onActivityResult(requestCode, resultCode, resultData)
        if (resultCode === Activity.RESULT_OK) {
            if (requestCode === REQUEST_CODE_READ_STORAGE) {
                if (resultData != null) {
                    val uri: Uri = resultData.getData()!!
                    updatedProfile(uri.toString())
                }
            }
        }
    }

    private fun updatedProfile(uri: String) {
        viewModel.updatedProfile(uri).observe(this, Observer {
            when(it){
                is Resource.Loading -> {
                    println("loading.....updated profile")
                }
                is Resource.Success -> {
                    loadPhoto(it.data.value!!)
                    Hawk.put("user_profile",it.data.value!!)
                }
                is Resource.Failure -> {
                    Log.e("Failed fetch profile==>",it.message)
                }
            }
        })
    }

    private fun loadPhoto(data: String) {
        val options: RequestOptions = RequestOptions()
            .skipMemoryCache(true)
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .placeholder(R.drawable.ic_account_circle)
            .error(R.drawable.ic_account_circle)
            .priority(Priority.HIGH)

        Glide.with(applicationContext)
            .asBitmap()
            .apply(options)
            .transform(CenterCrop())
            .load(data.toUri())
            .into(object : BitmapImageViewTarget(fab_profile) {
                override fun setResource(resource: Bitmap?) {
                    val circularBitmapDrawable = RoundedBitmapDrawableFactory.create(applicationContext.getResources(), resource)
                    circularBitmapDrawable.isCircular = true
                    fab_profile.setImageDrawable(circularBitmapDrawable)
                }
            })
    }
}