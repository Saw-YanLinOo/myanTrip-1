package com.vmyan.myantrip.ui.adapter.bookingCateAdapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.vmyan.myantrip.R
import com.vmyan.myantrip.model.bookingCate.BookingCateItem
import kotlinx.android.synthetic.main.booking_cat_item.view.*

class BookingCateAdapter(private val listener: ItemClickListener, private val items: MutableList<BookingCateItem>) : RecyclerView.Adapter<BookingCateAdapter.BookingCateViewHolder>(){
    interface ItemClickListener{
        fun onItemClick(bookingName : String,id: String)
    }
    fun setItems(items: List<BookingCateItem>){
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BookingCateAdapter.BookingCateViewHolder {
        val view= LayoutInflater.from(parent.context).inflate(R.layout.booking_cat_item,parent,false)
        return  BookingCateViewHolder(view,listener)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: BookingCateAdapter.BookingCateViewHolder, position: Int) {
        holder.bind(items[position])
    }

    class BookingCateViewHolder(private val view: View, private val listener: ItemClickListener):
        RecyclerView.ViewHolder(view), View.OnClickListener{
        private lateinit var bookingCateItem: BookingCateItem
        init {
            view.setOnClickListener ( this )
        }

        fun bind(item : BookingCateItem){
            this.bookingCateItem=item
            view.cat_name.text=item.txtBookingName
            Glide.with(view)
                .load(item.imgBooking)
                .into(view.cat_img)



        }


        override fun onClick(p0: View?) {
            listener.onItemClick(bookingCateItem.txtBookingName, bookingCateItem.cateId)

        }

    }


}