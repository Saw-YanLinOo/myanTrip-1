package com.vmyan.myantrip.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.smarteist.autoimageslider.SliderViewAdapter
import com.vmyan.myantrip.R
import kotlinx.android.synthetic.main.image_slider_layout_item.view.*

class PlaceImgSliderAdapter : SliderViewAdapter<PlaceImgSliderAdapter.PlaceImgSliderViewHolder>() {

    private var imgList: ArrayList<String> = arrayListOf()

    fun setItems(list: ArrayList<String>){
        imgList.clear()
        this.imgList = list
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup): PlaceImgSliderViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.image_slider_layout_item, parent, false)
        return PlaceImgSliderViewHolder(view)
    }

    override fun getCount(): Int {
        return this.imgList.size
    }

    override fun onBindViewHolder(viewHolder: PlaceImgSliderViewHolder, position: Int) {
            viewHolder.bind(imgList[position])
    }

    class PlaceImgSliderViewHolder(itemView: View) : SliderViewAdapter.ViewHolder(itemView) {

        fun bind(imgUrl: String){
            Glide.with(itemView)
                .load(imgUrl)
                .into(itemView.iv_auto_image_slider)
        }

    }

}