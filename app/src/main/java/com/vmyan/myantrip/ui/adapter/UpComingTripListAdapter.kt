package com.vmyan.myantrip.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.vmyan.myantrip.R
import com.vmyan.myantrip.model.Place
import com.vmyan.myantrip.model.Trip
import kotlinx.android.synthetic.main.search_place_item_layout.view.*
import kotlinx.android.synthetic.main.trip_item_layout.view.*
import java.text.SimpleDateFormat

class UpComingTripListAdapter(private val listener: ItemClickListener, private val items: MutableList<Trip>) : RecyclerView.Adapter<UpComingTripListAdapter.UpComingTripListViewHolder>() {
    interface ItemClickListener {
        fun onTripClick(tripId : String)
    }

    fun setItems(items: List<Trip>){
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UpComingTripListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.trip_item_layout,parent,false)
        return UpComingTripListViewHolder(view, listener)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: UpComingTripListViewHolder, position: Int) {
        holder.bind(items[position])
    }


    class UpComingTripListViewHolder(private val view: View, private val listener: ItemClickListener)
        :RecyclerView.ViewHolder(view), View.OnClickListener {

        private lateinit var trip: Trip

        init {
            view.setOnClickListener(this)
        }

        @SuppressLint("SetTextI18n", "SimpleDateFormat")
        fun bind(item: Trip){
            this.trip = item
            val start = item.tripStartDate
            val end = item.tripEndDate
            val pattern = "MMM, dd\nyyyy"
            val simpleDateFormat = SimpleDateFormat(pattern)
            val startDate = simpleDateFormat.format(start.toDate())
            val endDate = simpleDateFormat.format(end.toDate())

            view.trip_name.text = item.tripName
            view.trip_startdate.text = startDate
            view.trip_enddate.text = endDate
            Glide.with(view)
                .load(item.tripImg)
                .into(view.trip_img)
            view.trip_destination.text = item.tripDestination

            val interval = end.seconds - start.seconds
            val duration = ((interval/60)/60)/24

            view.trip_duration.text = "$duration days"
        }

        override fun onClick(p0: View?) {
            listener.onTripClick(trip.tripId)
        }

    }


}