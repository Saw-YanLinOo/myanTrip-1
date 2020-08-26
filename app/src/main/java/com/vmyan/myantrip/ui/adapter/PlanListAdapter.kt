package com.vmyan.myantrip.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.vmyan.myantrip.R
import com.vmyan.myantrip.model.TripPlan
import kotlinx.android.synthetic.main.activity_add_car_rental.view.*
import kotlinx.android.synthetic.main.plan_activity_item_layout.view.*
import kotlinx.android.synthetic.main.plan_bus_item_layout.view.*
import kotlinx.android.synthetic.main.plan_car_item_layout.view.*
import kotlinx.android.synthetic.main.plan_direction_item_layout.view.*
import kotlinx.android.synthetic.main.plan_flight_item_layout.view.*
import kotlinx.android.synthetic.main.plan_hotel_item_layout.view.*
import kotlinx.android.synthetic.main.plan_hotel_item_layout.view.hotel_address
import kotlinx.android.synthetic.main.plan_hotel_item_layout.view.hotel_cost_img
import kotlinx.android.synthetic.main.plan_note_item_layout.view.*
import kotlinx.android.synthetic.main.plan_parking_item_layout.view.*
import kotlinx.android.synthetic.main.plan_place_item_layout.view.*
import kotlinx.android.synthetic.main.plan_restaurant_item_layout.view.*
import kotlinx.android.synthetic.main.plan_shop_item_layout.view.*
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
            CARRENTAL -> {
                return CarRentalListViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.plan_car_item_layout, parent, false), listener)
            }
            NOTE -> {
                return NoteListViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.plan_note_item_layout, parent, false), listener)
            }
            DIRECTION -> {
                return DirectionListViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.plan_direction_item_layout, parent, false), listener)
            }
            PARKING -> {
                return ParkingListViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.plan_parking_item_layout, parent, false), listener)
            }
            ACTIVITY -> {
                return ActivityListViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.plan_activity_item_layout, parent, false), listener)
            }
            PLACE -> {
                return PlaceListViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.plan_place_item_layout, parent, false), listener)
            }
            SHOPPING -> {
                return ShopListViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.plan_shop_item_layout, parent, false), listener)
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
            CARRENTAL -> {
                (holder as CarRentalListViewHolder).bind(position)
            }
            NOTE -> {
                (holder as NoteListViewHolder).bind(position)
            }
            DIRECTION -> {
                (holder as DirectionListViewHolder).bind(position)
            }
            PARKING -> {
                (holder as ParkingListViewHolder).bind(position)
            }
            ACTIVITY -> {
                (holder as ActivityListViewHolder).bind(position)
            }
            PLACE -> {
                (holder as PlaceListViewHolder).bind(position)
            }
            SHOPPING -> {
                (holder as ShopListViewHolder).bind(position)
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

    private inner class CarRentalListViewHolder(private val view: View, private val listener: ItemClickListener)
        :RecyclerView.ViewHolder(view), View.OnClickListener {

        private lateinit var plan: TripPlan

        init {
            view.setOnClickListener(this)
        }

        @SuppressLint("SetTextI18n", "SimpleDateFormat")
        fun bind(position: Int){
            this.plan = items[position]

            if (position == 0){
                view.cartopbar.visibility = View.INVISIBLE
            }
            if (position == items.size -1){
                view.carbotbar.visibility = View.INVISIBLE
            }

            view.car_name.text = plan.name
            view.car_address.text = "${plan.address}, ${plan.city}, ${plan.state}"
            if (plan.status == "Pick Up"){
                view.car_cost_img.visibility = View.VISIBLE
                view.car_cost.visibility = View.VISIBLE
                view.car_cost.text = "${plan.estimationCost} MMK"
                view.car_from_to.text = "Drop Off"
            }else if(plan.status == "Arrival"){
                view.car_cost_img.visibility = View.GONE
                view.car_cost.visibility = View.GONE
                view.car_from_to.text = "End"
            }
            view.car_time.text = SimpleDateFormat("hh:mm a").format(plan.date.toDate())
            view.car_date.text = SimpleDateFormat("MMM, dd\nyyyy").format(plan.date.toDate())
            view.car_status.text = plan.status
            view.car_type.text = plan.type

            Glide.with(view).load(plan.img).into(view.carimg)
        }

        override fun onClick(p0: View?) {
            listener.onPlaceClick(plan.plan_id)
        }

    }

    private inner class NoteListViewHolder(private val view: View, private val listener: ItemClickListener)
        :RecyclerView.ViewHolder(view), View.OnClickListener {

        private lateinit var plan: TripPlan

        init {
            view.setOnClickListener(this)
        }

        @SuppressLint("SetTextI18n", "SimpleDateFormat")
        fun bind(position: Int){
            this.plan = items[position]

            if (position == 0){
                view.notetopbar.visibility = View.INVISIBLE
            }
            if (position == items.size -1){
                view.notebotbar.visibility = View.INVISIBLE
            }

            view.note_title.text = plan.name
            view.note_address.text = "${plan.address}, ${plan.city}, ${plan.state}"
            view.note_time.text = SimpleDateFormat("hh:mm a").format(plan.date.toDate())
            view.note_date.text = SimpleDateFormat("MMM, dd\nyyyy").format(plan.date.toDate())
            view.note_details.text = plan.details

            Glide.with(view).load(plan.img).into(view.noteimg)
        }

        override fun onClick(p0: View?) {
            listener.onPlaceClick(plan.plan_id)
        }

    }

    private inner class DirectionListViewHolder(private val view: View, private val listener: ItemClickListener)
        :RecyclerView.ViewHolder(view), View.OnClickListener {

        private lateinit var plan: TripPlan

        init {
            view.setOnClickListener(this)
        }

        @SuppressLint("SetTextI18n", "SimpleDateFormat")
        fun bind(position: Int){
            this.plan = items[position]

            if (position == 0){
                view.dirtopbar.visibility = View.INVISIBLE
            }
            if (position == items.size -1){
                view.dirbotbar.visibility = View.INVISIBLE
            }

            view.dir_name.text = plan.name
            view.dir_address.text = "${plan.address}, ${plan.city}, ${plan.state}"
            if (plan.status == "Start"){
                view.dir_from_to.text = "From"
            }else if(plan.status == "Destination"){
                view.dir_from_to.text = "To"
            }
            view.dir_time.text = SimpleDateFormat("hh:mm a").format(plan.date.toDate())
            view.dir_date.text = SimpleDateFormat("MMM, dd\nyyyy").format(plan.date.toDate())
            view.dir_status.text = plan.status

            Glide.with(view).load(plan.img).into(view.dirimg)
        }

        override fun onClick(p0: View?) {
            listener.onPlaceClick(plan.plan_id)
        }

    }

    private inner class ParkingListViewHolder(private val view: View, private val listener: ItemClickListener)
        :RecyclerView.ViewHolder(view), View.OnClickListener {

        private lateinit var plan: TripPlan

        init {
            view.setOnClickListener(this)
        }

        @SuppressLint("SetTextI18n", "SimpleDateFormat")
        fun bind(position: Int){
            this.plan = items[position]

            if (position == 0){
                view.parkingtopbar.visibility = View.INVISIBLE
            }
            if (position == items.size -1){
                view.parkingbotbar.visibility = View.INVISIBLE
            }

            view.parking_name.text = plan.name
            view.parking_address.text = "${plan.address}, ${plan.city}, ${plan.state}"
            if (plan.status == "Drop Off"){
                view.parking_cost_img.visibility = View.VISIBLE
                view.parking_cost.visibility = View.VISIBLE
                view.parking_cost.text = "${plan.estimationCost} MMK"
            }else if(plan.status == "Pick Up"){
                view.parking_cost_img.visibility = View.GONE
                view.parking_cost.visibility = View.GONE
            }
            view.parkingtime.text = SimpleDateFormat("hh:mm a").format(plan.date.toDate())
            view.parking_date.text = SimpleDateFormat("MMM, dd\nyyyy").format(plan.date.toDate())
            view.parking_status.text = plan.status

            Glide.with(view).load(plan.img).into(view.parkingimg)
        }

        override fun onClick(p0: View?) {
            listener.onPlaceClick(plan.plan_id)
        }

    }

    private inner class ActivityListViewHolder(private val view: View, private val listener: ItemClickListener)
        :RecyclerView.ViewHolder(view), View.OnClickListener {

        private lateinit var plan: TripPlan

        init {
            view.setOnClickListener(this)
        }

        @SuppressLint("SetTextI18n", "SimpleDateFormat")
        fun bind(position: Int){
            this.plan = items[position]

            if (position == 0){
                view.acttopbar.visibility = View.INVISIBLE
            }
            if (position == items.size -1){
                view.actbotbar.visibility = View.INVISIBLE
            }

            view.act_name.text = plan.name
            view.act_address.text = "${plan.address}, ${plan.city}, ${plan.state}"
            view.act_time.text = SimpleDateFormat("hh:mm a").format(plan.date.toDate())
            view.activity_date.text = SimpleDateFormat("MMM, dd\nyyyy").format(plan.date.toDate())
            view.activity_status.text = plan.status

            Glide.with(view).load(plan.img).into(view.actimg)
        }

        override fun onClick(p0: View?) {
            listener.onPlaceClick(plan.plan_id)
        }

    }

    private inner class PlaceListViewHolder(private val view: View, private val listener: ItemClickListener)
        :RecyclerView.ViewHolder(view), View.OnClickListener {

        private lateinit var plan: TripPlan

        init {
            view.setOnClickListener(this)
        }

        @SuppressLint("SetTextI18n", "SimpleDateFormat")
        fun bind(position: Int){
            this.plan = items[position]

            if (position == 0){
                view.placetopbar.visibility = View.INVISIBLE
            }
            if (position == items.size -1){
                view.placebotbar.visibility = View.INVISIBLE
            }

            view.place_name.text = plan.name
            view.place_address.text = "${plan.address}, ${plan.city}, ${plan.state}"
            view.place_time.text = SimpleDateFormat("hh:mm a").format(plan.date.toDate())
            view.place_date.text = SimpleDateFormat("MMM, dd\nyyyy").format(plan.date.toDate())

            Glide.with(view).load(plan.img).into(view.placeimg)
        }

        override fun onClick(p0: View?) {
            listener.onPlaceClick(plan.plan_id)
        }

    }

    private inner class ShopListViewHolder(private val view: View, private val listener: ItemClickListener)
        :RecyclerView.ViewHolder(view), View.OnClickListener {

        private lateinit var plan: TripPlan

        init {
            view.setOnClickListener(this)
        }

        @SuppressLint("SetTextI18n", "SimpleDateFormat")
        fun bind(position: Int){
            this.plan = items[position]

            if (position == 0){
                view.stopbar.visibility = View.INVISIBLE
            }
            if (position == items.size -1){
                view.sbotbar.visibility = View.INVISIBLE
            }

            view.shop_name.text = plan.name
            view.shop_address.text = "${plan.address}, ${plan.city}, ${plan.state}"
            view.shop_time.text = SimpleDateFormat("hh:mm a").format(plan.date.toDate())
            view.shop_date.text = SimpleDateFormat("MMM, dd\nyyyy").format(plan.date.toDate())
            view.shop_type.text = plan.type
            view.shop_cost.text = "${plan.estimationCost} MMK"
            Glide.with(view).load(plan.img).into(view.shopimg)
        }

        override fun onClick(p0: View?) {
            listener.onPlaceClick(plan.plan_id)
        }

    }

}