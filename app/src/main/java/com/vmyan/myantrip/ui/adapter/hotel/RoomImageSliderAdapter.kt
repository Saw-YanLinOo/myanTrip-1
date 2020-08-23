package com.vmyan.myantrip.ui.adapter.hotel

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.smarteist.autoimageslider.SliderViewAdapter
import com.vmyan.myantrip.R
import kotlinx.android.synthetic.main.room_img_slider_view.view.*

class RoomImageSliderAdapter : SliderViewAdapter<RoomImageSliderAdapter.RoomImageViewHolder>() {

    private var roomImgList : ArrayList<String> = arrayListOf()

    fun setItem(rImgList: List<String>){
        roomImgList.clear()
        roomImgList.addAll(rImgList)
        notifyDataSetChanged()


    }


    override fun onCreateViewHolder(parent: ViewGroup?): RoomImageViewHolder {
        val view = LayoutInflater.from(parent?.context).inflate(R.layout.room_img_slider_view, parent,false);
        return RoomImageViewHolder(view)
    }

    override fun getCount(): Int {
        return roomImgList.size
    }

    override fun onBindViewHolder(viewHolder: RoomImageViewHolder?, position: Int) {
        viewHolder?.bind(roomImgList[position])
    }
    class RoomImageViewHolder(view : View) : SliderViewAdapter.ViewHolder(view) {
        fun bind(rImgSliderList: String){
            Glide.with(itemView)
                .load(rImgSliderList)
                .into(itemView.roomImageSlider)


        }

    }
}