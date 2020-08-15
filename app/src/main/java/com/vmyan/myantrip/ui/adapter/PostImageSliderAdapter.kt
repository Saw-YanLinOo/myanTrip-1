package com.vmyan.myantrip.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.smarteist.autoimageslider.SliderViewAdapter
import com.vmyan.myantrip.R
import kotlinx.android.synthetic.main.image_slider_layout_item.view.*

class PostImageSliderAdapter: SliderViewAdapter<PostImageSliderAdapter.SliderAdapterVH>() {
    private val imageList:ArrayList<String> = arrayListOf()

    fun setItems(imageList: List<String>){
        this.imageList.clear()
        this.imageList.addAll(imageList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup): SliderAdapterVH {
        val view =LayoutInflater.from(parent.context).inflate(R.layout.image_slider_layout_item, null);
        return SliderAdapterVH(view)
    }

    override fun getCount(): Int {
        return imageList.size
    }

    override fun onBindViewHolder(viewHolder: SliderAdapterVH, position: Int) {
        (viewHolder as SliderAdapterVH).bind(imageList[position])
    }

    class SliderAdapterVH(itemView: View) :SliderViewAdapter.ViewHolder(itemView) {
        fun bind(imgList: String){
            Glide.with(itemView)
                .load(imgList)
                .into(itemView.iv_auto_image_slider)
        }
    }

}