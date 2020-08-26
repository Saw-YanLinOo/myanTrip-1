package com.vmyan.myantrip.ui.adapter


import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.vmyan.myantrip.R
import com.vmyan.myantrip.model.BookingTicket
import com.vmyan.myantrip.model.Review
import com.vmyan.myantrip.model.User
import kotlinx.android.synthetic.main.review_item_layout.view.*
import kotlinx.android.synthetic.main.ticket_layout_item.view.*
import kotlinx.android.synthetic.main.trip_plan_users_item_layout.view.*
import java.text.SimpleDateFormat

class TicketListAdapter(private val items: MutableList<BookingTicket>)
    : RecyclerView.Adapter<TicketListAdapter.TicketListViewHolder>() {

    fun setItems(items: List<BookingTicket>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TicketListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.ticket_layout_item,parent,false)
        return TicketListViewHolder(view)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: TicketListViewHolder, position: Int) {
        holder.bind(items[position])
    }


    class TicketListViewHolder( private val view: View)
        :RecyclerView.ViewHolder(view) {


        @SuppressLint("SimpleDateFormat")
        fun bind(item: BookingTicket){
            view.tName.text = item.tName
            view.tprice.text = "${item.totalCost} MMK"
            view.fromto.text = "${item.dateFrom} - ${item.dateTo}"
            Glide.with(view)
                .load(item.tTypeImage)
                .into(view.userimg)
        }



    }
}