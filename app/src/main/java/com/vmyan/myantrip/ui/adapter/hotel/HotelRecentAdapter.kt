package com.vmyan.myantrip.ui.adapter.hotel

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.vmyan.myantrip.R
import com.vmyan.myantrip.model.hotel.HotelRecentItem
import kotlinx.android.synthetic.main.hotel_recent_item.view.*

class HotelRecentAdapter internal constructor(context: Context):RecyclerView.Adapter<HotelRecentAdapter.HotelRecentViewHolder>(){
        private  val inflate:LayoutInflater= LayoutInflater.from(context)
        private var items= emptyList<HotelRecentItem>()
    inner class HotelRecentViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        private lateinit var hotelRecentItem : HotelRecentItem

        fun bind(items :HotelRecentItem){
            this.hotelRecentItem=items
            Glide.with(itemView)
                .load(items.cityImage)
                .into(itemView.appImgCity_recent)
            itemView.appTxtTownNameSel.text=items.townName
            itemView.txt_recentSdate.text=items.sDate
            itemView.txt_recentLdate.text=items.lDate
            itemView.txtNoOfGuests.text=items.noOfGuests
            itemView.txtNoOfRoom.text=items.noOfRoom

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HotelRecentViewHolder {
        val itemView=inflate.inflate(R.layout.hotel_recent_item,parent,false)
        return HotelRecentViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: HotelRecentViewHolder, position: Int) {
        holder.bind(items[position])

    }

    internal fun setWords(items: List<HotelRecentItem>) {
        this.items = items

        notifyDataSetChanged()
    }

}