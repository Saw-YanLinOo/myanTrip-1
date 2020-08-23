package com.vmyan.myantrip.ui.adapter.flight

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.vmyan.myantrip.R
import com.vmyan.myantrip.model.flight.FlightListItem
import kotlinx.android.synthetic.main.flight_view_item.view.*

class FlightListAdapeter (private val listener: ItemClickListener, private val items: MutableList<FlightListItem>): RecyclerView.Adapter<FlightListAdapeter.FlightListViewHolder>() {
    interface ItemClickListener {
        fun onItemClick(id: String)
    }


    fun setItems(items: List<FlightListItem>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FlightListViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.flight_view_item, parent, false)
        return FlightListViewHolder(view, listener)
    }


    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: FlightListViewHolder, position: Int) {
        holder.bind(items[position])
    }

    class FlightListViewHolder(
        private val view: View,
        private val listener: ItemClickListener
    ) : RecyclerView.ViewHolder(view) {
        private lateinit var flightList: FlightListItem

        fun bind(item : FlightListItem){
            this.flightList=item
            view.txtFlightName.text=item.flightName
            view.txt_SPlace.text = item.flightFrom
            view.txt_EndPlace.text=item.flightTo
            view.txt_DepartTime.text=item.fTimeStart
            view.txt_ReturnTime.text=item.fTimeStop
            view.txt_DepartDateValue.text=item.flightDeparture
            view.txtflightValue.text=item.flightPrice

            Glide.with(view)
                .load(item.flightImage)
                .into(view.img_flight)

            view.card_chooseFlight.setOnClickListener {
                listener.onItemClick(item.flightId)
            }
        }



    }
}