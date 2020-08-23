package com.vmyan.myantrip.ui.adapter.flight

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.vmyan.myantrip.R
import com.vmyan.myantrip.model.flight.FlightRecentItem
import kotlinx.android.synthetic.main.flight_recent_item.view.*

class FlightRecentAdapter internal constructor(context: Context):RecyclerView.Adapter<FlightRecentAdapter.FlightRecentViewHolder>() {
    private  val inflate: LayoutInflater = LayoutInflater.from(context)
    private var items= emptyList<FlightRecentItem>()

    inner class FlightRecentViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        private lateinit var flightRecentItem : FlightRecentItem

        fun bind(items : FlightRecentItem){
            this.flightRecentItem=items

            itemView.flightRecentFrom.text=items.flightFrom
            itemView.flightRecentTo.text=items.flightTo
            itemView.flightRecentDepartDate.text=items.flightDepartDate
            itemView.flightRecentAdults.text=items.fAdultsCount
            itemView.flightRecentChild.text=items.fChildCount
            itemView.flightRecentInfant.text=items.fInfantCount

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FlightRecentViewHolder {
        val itemView=inflate.inflate(R.layout.flight_recent_item,parent,false)
        return FlightRecentViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: FlightRecentViewHolder, position: Int) {
        holder.bind(items[position])

    }

    internal fun setWords(items: List<FlightRecentItem>) {
        this.items = items

        notifyDataSetChanged()
    }
}