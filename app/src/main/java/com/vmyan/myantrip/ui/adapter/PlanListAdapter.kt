package com.vmyan.myantrip.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.vmyan.myantrip.R
import com.vmyan.myantrip.model.TripPlan
import kotlinx.android.synthetic.main.plan_bus_item_layout.view.*
import kotlinx.android.synthetic.main.plan_flight_item_layout.view.*
import kotlinx.android.synthetic.main.plan_hotel_item_layout.view.*
import kotlinx.android.synthetic.main.plan_hotel_item_layout.view.hotel_address
import kotlinx.android.synthetic.main.plan_hotel_item_layout.view.hotel_cost_img
import kotlinx.android.synthetic.main.plan_restaurant_item_layout.view.*
import kotlinx.android.synthetic.main.plan_train_item_layout.view.*
import java.text.SimpleDateFormat

class PlanListAdapter(private val listener: ItemClickListener, private val items: MutableList<TripPlan>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    interface ItemClickListener {
        fun onPlaceClick(plan_id : String)
    }

    companion object {
        const val HOTEL = 1
        const val RESTAURANT = 2
        const val BUS = 3
        const val FLIGHT = 4
        const val RAIL = 5
        const val CARRENTAL = 6
        const val NOTE = 7
        const val DIRECTION = 8
        const val PARKING = 9
        const val ACTIVITY = 10
        const val PLACE = 11
        const val SHOPPING = 12
    }

    fun setItems(items: List<TripPlan>){
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        when(viewType){
            HOTEL -> {
                return HotelListViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.plan_hotel_item_layout, parent, false), listener)
            }
            RESTAURANT -> {
                return RestaurantListViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.plan_restaurant_item_layout, parent, false), listener)

            }
            BUS -> {
                return BusListViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.plan_bus_item_layout, parent, false), listener)
            }
            FLIGHT -> {
                return FlightListViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.plan_flight_item_layout, parent, false), listener)
            }
            RAIL -> {
                return TrainListViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.plan_train_item_layout, parent, false), listener)
            }
        }

        return HotelListViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.plan_hotel_item_layout, parent, false), listener)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(items[position].viewType){
            HOTEL -> {
                (holder as HotelListViewHolder).bind(position)
            }
            RESTAURANT -> {
                (holder as RestaurantListViewHolder).bind(position)
            }
            BUS -> {
                (holder as BusListViewHolder).bind(position)
            }
            FLIGHT -> {
                (holder as FlightListViewHolder).bind(position)
            }
            RAIL -> {
                (holder as TrainListViewHolder).bind(position)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return items[position].viewType
    }


    private inner class HotelListViewHolder(private val view: View, private val listener: ItemClickListener)
        :RecyclerView.ViewHolder(view), View.OnClickListener {

        private lateinit var plan: TripPlan

        init {
            view.setOnClickListener(this)
        }

        @SuppressLint("SetTextI18n", "SimpleDateFormat")
        fun bind(position: Int){
            this.plan = items[position]

            if (position == 0){
                view.htopbar.visibility = View.INVISIBLE
            }
            if (position == items.size -1){
                view.hbotbar.visibility = View.INVISIBLE
            }

            view.hotel_name.text = plan.name
            view.hotel_address.text = "${plan.address}, ${plan.city}, ${plan.state}"
            view.hotel_date.text = SimpleDateFormat("MMM, dd\nyyyy").format(plan.date.toDate())
            view.hotel_time.text = SimpleDateFormat("hh:mm a").format(plan.date.toDate())
            view.hotel_status.text = plan.status
            if (plan.status == "Check In"){
                view.hotel_cost_img.visibility = View.VISIBLE
                view.hotel_cost.visibility = View.VISIBLE
                view.hotel_cost.text = "${plan.estimationCost} MMK"
            }else if(plan.status == "Check Out"){
                view.hotel_cost_img.visibility = View.GONE
                view.hotel_cost.visibility = View.GONE
            }

            Glide.with(view).load(plan.img).into(view.hotel_img)
        }

        override fun onClick(p0: View?) {
            listener.onPlaceClick(plan.plan_id)
        }

    }

    private inner class RestaurantListViewHolder(private val view: View, private val listener: ItemClickListener)
        :RecyclerView.ViewHolder(view), View.OnClickListener {

        private lateinit var plan: TripPlan

        init {
            view.setOnClickListener(this)
        }

        @SuppressLint("SetTextI18n", "SimpleDateFormat")
        fun bind(position: Int){
            this.plan = items[position]

            if (position == 0){
                view.rtopbar.visibility = View.INVISIBLE
            }
            if (position == items.size -1){
                view.rbotbar.visibility = View.INVISIBLE
            }

            view.restaurant_name.text = plan.name
            view.restaurant_address.text = "${plan.address}, ${plan.city}, ${plan.state}"
            view.restaurant_cost.text = "${plan.estimationCost} MMK"
            view.restaurant_time.text = SimpleDateFormat("hh:mm a").format(plan.date.toDate())
            view.restaurant_date.text = SimpleDateFormat("MMM, dd\nyyyy").format(plan.date.toDate())

            Glide.with(view).load(plan.img).into(view.restaurant_img)
        }

        override fun onClick(p0: View?) {
            listener.onPlaceClick(plan.plan_id)
        }

    }

    private inner class BusListViewHolder(private val view: View, private val listener: ItemClickListener)
        :RecyclerView.ViewHolder(view), View.OnClickListener {

        private lateinit var plan: TripPlan

        init {
            view.setOnClickListener(this)
        }

        @SuppressLint("SetTextI18n", "SimpleDateFormat")
        fun bind(position: Int){
            this.plan = items[position]

            if (position == 0){
                view.bustopbar.visibility = View.INVISIBLE
            }
            if (position == items.size -1){
                view.busbotbar.visibility = View.INVISIBLE
            }

            view.bus_name.text = plan.name
            view.bus_address.text = "${plan.address}, ${plan.city}, ${plan.state}"
            if (plan.status == "Departure"){
                view.bus_cost_img.visibility = View.VISIBLE
                view.bus_cost.visibility = View.VISIBLE
                view.bus_cost.text = "${plan.estimationCost} MMK"
                view.bus_from_to.text = "From"
            }else if(plan.status == "Arrival"){
                view.bus_cost_img.visibility = View.GONE
                view.bus_cost.visibility = View.GONE
                view.bus_from_to.text = "To"
            }
            view.bus_time.text = SimpleDateFormat("hh:mm a").format(plan.date.toDate())
            view.bus_date.text = SimpleDateFormat("MMM, dd\nyyyy").format(plan.date.toDate())
            view.bus_status.text = plan.status
            view.bus_type.text = plan.type

            Glide.with(view).load(plan.img).into(view.busimg)
        }

        override fun onClick(p0: View?) {
            listener.onPlaceClick(plan.plan_id)
        }

    }


    private inner class FlightListViewHolder(private val view: View, private val listener: ItemClickListener)
        :RecyclerView.ViewHolder(view), View.OnClickListener {

        private lateinit var plan: TripPlan

        init {
            view.setOnClickListener(this)
        }

        @SuppressLint("SetTextI18n", "SimpleDateFormat")
        fun bind(position: Int){
            this.plan = items[position]

            if (position == 0){
                view.flighttopbar.visibility = View.INVISIBLE
            }
            if (position == items.size -1){
                view.flightbotbar.visibility = View.INVISIBLE
            }

            view.flight_name.text = plan.name
            view.flight_address.text = "${plan.address}, ${plan.city}, ${plan.state}"
            if (plan.status == "Departure"){
                view.flight_cost_img.visibility = View.VISIBLE
                view.flight_cost.visibility = View.VISIBLE
                view.flight_cost.text = "${plan.estimationCost} MMK"
                view.flight_from_to.text = "Flying From"
            }else if(plan.status == "Arrival"){
                view.flight_cost_img.visibility = View.GONE
                view.flight_cost.visibility = View.GONE
                view.flight_from_to.text = "Flying To"
            }
            view.flight_time.text = SimpleDateFormat("hh:mm a").format(plan.date.toDate())
            view.flight_date.text = SimpleDateFormat("MMM, dd\nyyyy").format(plan.date.toDate())
            view.flight_status.text = plan.status
            view.flight_number.text = plan.type
            view.flight_terminal.text = plan.description
            view.flight_gate.text = plan.details

            Glide.with(view).load(plan.img).into(view.flightimg)
        }

        override fun onClick(p0: View?) {
            listener.onPlaceClick(plan.plan_id)
        }

    }

    private inner class TrainListViewHolder(private val view: View, private val listener: ItemClickListener)
        :RecyclerView.ViewHolder(view), View.OnClickListener {

        private lateinit var plan: TripPlan

        init {
            view.setOnClickListener(this)
        }

        @SuppressLint("SetTextI18n", "SimpleDateFormat")
        fun bind(position: Int){
            this.plan = items[position]

            if (position == 0){
                view.railtopbar.visibility = View.INVISIBLE
            }
            if (position == items.size -1){
                view.railbotbar.visibility = View.INVISIBLE
            }

            view.rail_name.text = plan.name
            view.rail_address.text = "${plan.address}, ${plan.city}, ${plan.state}"
            if (plan.status == "Departure"){
                view.rail_cost_img.visibility = View.VISIBLE
                view.rail_cost.visibility = View.VISIBLE
                view.rail_cost.text = "${plan.estimationCost} MMK"
                view.rail_from_to.text = "From"
            }else if(plan.status == "Arrival"){
                view.rail_cost_img.visibility = View.GONE
                view.rail_cost.visibility = View.GONE
                view.rail_from_to.text = "To"
            }
            view.rail_time.text = SimpleDateFormat("hh:mm a").format(plan.date.toDate())
            view.rail_date.text = SimpleDateFormat("MMM, dd\nyyyy").format(plan.date.toDate())
            view.rail_status.text = plan.status
            view.rail_type.text = plan.type

            Glide.with(view).load(plan.img).into(view.railimg)
        }

        override fun onClick(p0: View?) {
            listener.onPlaceClick(plan.plan_id)
        }

    }

}