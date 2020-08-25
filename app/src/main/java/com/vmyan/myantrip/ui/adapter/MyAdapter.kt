package com.vmyan.myantrip.ui.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.vmyan.myantrip.R
import kotlinx.android.synthetic.main.image_recycler_adapter.view.*

class MyAdapter(imageList: ArrayList<String>): RecyclerView.Adapter<MyAdapter.ImageViewHolder>() {

    private var imgList = imageList

    fun setItems(postList: ArrayList<String>){
        this.imgList.clear()
        this.imgList.addAll(postList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.image_recycler_adapter,
            parent,
            false)
        return MyAdapter.ImageViewHolder(view)
    }

    override fun getItemCount(): Int {
        return imgList.size
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        holder.bind(imgList[position])
    }
    class ImageViewHolder(view: View): RecyclerView.ViewHolder(view){
        fun bind(image: String){
            Log.w("imageUrl====>", image)
            Glide.with(itemView)
                .load(image.toUri())
                .skipMemoryCache(true) //2
                .diskCacheStrategy(DiskCacheStrategy.NONE) //3
                .into(itemView.imageView)
        }
    }
}