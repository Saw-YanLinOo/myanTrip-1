package com.vmyan.myantrip.ui.adapter.hotel

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.vmyan.myantrip.R
import com.vmyan.myantrip.model.hotel.HotelList
import kotlinx.android.synthetic.main.booking_cat_item.view.*
import kotlinx.android.synthetic.main.hotel_view_list.view.*
import kotlinx.android.synthetic.main.show_empty_message.view.*

class HotelViewAdapter(private val listener: ItemClickListener, private val items: MutableList<HotelList>): RecyclerView.Adapter<HotelViewAdapter.HotelListViewHolder>() {
    interface ItemClickListener {
        fun onItemClick(id: String)
    }


    fun setItems(items: List<HotelList>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): HotelViewAdapter.HotelListViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.hotel_view_list, parent, false)
        return HotelViewAdapter.HotelListViewHolder(view, listener)
    }


    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: HotelListViewHolder, position: Int) {
            holder.bind(items[position])
    }

    class HotelListViewHolder(
        private val view: View,
        private val listener:ItemClickListener
    ) : RecyclerView.ViewHolder(view), View.OnClickListener {
        private lateinit var sortList: HotelList
        init {
            view.setOnClickListener(this)
        }
        fun bind(item : HotelList){
            this.sortList=item
            view.hotelName.text=item.hotelName
           val hoteladdress=item.hotel_address+","+item.hotel_city+","+ item.hotel_country
            view.hotelLocation.text=hoteladdress

            view.hotel_rating_value.text=item.ratingValue
            view.room_lowestPrice.text=item.lowestPrice.toString()

            view.hotel_rating_bar.rating=item.ratingValue.toFloat()

            view.llWifi.visibility = View.GONE
            if (item.wifi){
                view.llWifi.visibility=View.VISIBLE
            }
            view.llBreakFast.visibility=View.GONE
            if (item.breakFast){
                view.llBreakFast.visibility=View.VISIBLE
            }
            view.llWaterPool.visibility=View.GONE
            if (item.waterPool){
                view.llWaterPool.visibility=View.VISIBLE
            }
            view.llCarPack.visibility=View.GONE
            if (item.carPack){
                view.llCarPack.visibility=View.VISIBLE
            }
            view.llRestaurant.visibility=View.GONE
            if (item.restaurant){
                view.llRestaurant.visibility=View.VISIBLE
            }

            Glide.with(view)
                .load(item.hotel_image)
                .into(view.hotelImage)




           /* view.wifi.text=item.wifi
            view.carPack.text=item.carPack
            view.waterPool.text=item.waterPool
            view.restaurant.text=item.restaurant*/

           // view.rating_value.text=item.ratingValue
            //view.lowestPrice.text=item.lowestPrice





        }

        override fun onClick(p0: View?) {
            listener.onItemClick(sortList.hotelId)

        }
    }
}