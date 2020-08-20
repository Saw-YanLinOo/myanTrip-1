package com.vmyan.myantrip.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.vmyan.myantrip.R
import com.vmyan.myantrip.model.TripPlan
import kotlinx.android.synthetic.main.plan_hotel_item_layout.view.*
import kotlinx.android.synthetic.main.plan_hotel_item_layout.view.hotel_address
import kotlinx.android.synthetic.main.plan_restaurant_item_layout.view.*
import java.text.SimpleDateFormat

class PlanListAdapter(private val listener: ItemClickListener, private val items: MutableList<TripPlan>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    interface ItemClickListener {
        fun onPlaceClick(plan_id : String)
    }

    companion object {
        const val HOTEL = 1
        const val RESTAURANT = 2
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




}