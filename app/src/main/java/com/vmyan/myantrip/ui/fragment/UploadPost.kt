package com.vmyan.myantrip.ui.fragment

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.vmyan.myantrip.R
import com.vmyan.myantrip.ui.viewmodel.UploadViewModel
import com.vmyan.myantrip.utils.Resource
import com.vmyan.myantrip.utils.showToast
import kotlinx.android.synthetic.main.fragment_upload_post.view.*
import kotlinx.android.synthetic.main.image_recycler_adapter.view.*
import org.koin.android.ext.android.inject
import kotlin.collections.ArrayList


class UploadPost : Fragment() {

    private val viewModel: UploadViewModel by inject()

    private lateinit var adapter : MyAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_upload_post, container, false)

        val bundle = this.arguments
        if (bundle != null) {
            if (bundle.getString("single_Photo") != null){
                view.multile_layout.visibility = View.GONE
                view.single_layout.visibility = View.VISIBLE
                SingleUpload(view, bundle.getString("single_Photo")!!)
            }
            if (bundle.getStringArrayList("multiple_Photo") != null){
                view.single_layout.visibility = View.GONE
                view.multile_layout.visibility = View.VISIBLE
                MultiUpload(view, bundle.getStringArrayList("multiple_Photo")!!)
            }
        }
        return view
    }

    private fun SingleUpload(view: View,data:String) {
        view.single_image.setImageURI(data.toUri())

        view.btn_single_upload.setOnClickListener{

            println("single description===> "+view.single_description.text.toString())
            var arrayList = ArrayList<String>()
            arrayList.add(data)
            setUpObserve(view, view.single_description.text.toString(),arrayList)
        }
    }

    private fun MultiUpload(view: View,data:ArrayList<String>) {
        adapter = MyAdapter(data)
        view.multi_recyclerView.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        view.multi_recyclerView.adapter = adapter

        view.btn_multi_Upload.setOnClickListener{

            println("Multi description===> "+view.multi_description.text.toString())
            setUpObserve(view,view.multi_description.text.toString(),data)
        }
    }
    @SuppressLint("showToast")
    private fun setUpObserve(view: View,description:String,iamgeList : ArrayList<String>) {

        viewModel.uploadPhoto(iamgeList).observe(viewLifecycleOwner, Observer {
            when(it){
                is Resource.Loading -> {
                    view.spin_kit_u.visibility = View.VISIBLE
                    println("loading.....uplaod Photo")
                }
                is Resource.Success -> {
                    var resultList = it.data.value!!
                    Log.e("Result Image List==>",resultList.toString())
                    viewModel.setPost(description,resultList).observe(viewLifecycleOwner, Observer {
                        when(it){
                            is Resource.Loading -> {
                                view.spin_kit_u.visibility = View.VISIBLE
                                println("loading.....PostList")
                            }
                            is Resource.Success -> {
                                view.spin_kit_u.visibility = View.GONE
                                requireContext().showToast("Upload Successful")
                                gotoBlog()
                            }
                            is Resource.Failure -> {
                                view.spin_kit_u.visibility = View.GONE
                                println("Can't Upload post "+it.message)
                            }
                        }
                    })
                }
                is Resource.Failure -> {
                    view.spin_kit_u.visibility = View.GONE
                    println("Can't Upload Photo "+it.message)
                }
            }
        })
    }

    private fun gotoBlog() {
        val ft: FragmentTransaction = requireFragmentManager().beginTransaction()
        if (Build.VERSION.SDK_INT >= 26) {
            ft.setReorderingAllowed(false)
        }
        ft.detach(BlogFragment()).attach(BlogFragment()).commit()

        requireActivity().finish()
    }
}
class MyAdapter(imageList:ArrayList<String>): RecyclerView.Adapter<MyAdapter.ImageViewHolder>() {

    private var imgList = imageList

    fun setItems(postList: ArrayList<String>){
        this.imgList.clear()
        this.imgList.addAll(postList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.image_recycler_adapter,parent,false)
        return MyAdapter.ImageViewHolder(view)
    }

    override fun getItemCount(): Int {
        return imgList.size
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        holder.bind(imgList[position])
    }
    class ImageViewHolder( view : View):RecyclerView.ViewHolder(view){
        fun bind(image : String){
            Log.w("imageUrl====>",image)
            Glide.with(itemView)
                .load(image.toUri())
                .skipMemoryCache(true) //2
                .diskCacheStrategy(DiskCacheStrategy.NONE) //3
                .into(itemView.imageView)
        }
    }
}