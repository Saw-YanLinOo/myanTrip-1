package com.vmyan.myantrip.ui.adapter.promoImageAdapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.smarteist.autoimageslider.SliderViewAdapter
import com.vmyan.myantrip.R
import kotlinx.android.synthetic.main.image_slide_view.view.*

class PromoSliderImageAdapter : SliderViewAdapter<PromoSliderImageAdapter.SliderAdapterVH>() {

    private var imgList: ArrayList<String> = arrayListOf()
    fun setItem(imgList1: List<String>){
        imgList.clear()
        imgList.addAll(imgList1)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup): SliderAdapterVH {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.image_slide_view, parent,false);
        return SliderAdapterVH(view)
    }

    override fun getCount(): Int {
        return imgList.size
    }

    override fun onBindViewHolder(viewHolder: SliderAdapterVH, position: Int) {
        viewHolder.bind(imgList[position])

    }
    class SliderAdapterVH(view: View) : SliderViewAdapter.ViewHolder(view) {
        fun bind(imgList: String){

            Glide.with(itemView)
                .load(imgList)
                .into(itemView.iv_promo_image_slider)
        }


    }
}