package com.vmyan.myantrip.ui.adapter


import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.vmyan.myantrip.R
import com.vmyan.myantrip.model.Emergency
import com.vmyan.myantrip.model.Review
import com.vmyan.myantrip.model.User
import kotlinx.android.synthetic.main.emergency_item_layout.view.*
import kotlinx.android.synthetic.main.review_item_layout.view.*
import kotlinx.android.synthetic.main.trip_plan_users_item_layout.view.*
import java.text.SimpleDateFormat

class EmergencyListAdapter(private val items: MutableList<Emergency>)
    : RecyclerView.Adapter<EmergencyListAdapter.EmergencyListViewHolder>() {

    fun setItems(items: List<Emergency>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EmergencyListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.emergency_item_layout,parent,false)
        return EmergencyListViewHolder(view)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: EmergencyListViewHolder, position: Int) {
        holder.bind(items[position])
    }


    class EmergencyListViewHolder( private val view: View)
        :RecyclerView.ViewHolder(view) {


        @SuppressLint("SimpleDateFormat", "SetTextI18n")
        fun bind(item: Emergency){
            view.ecity.text = item.city
            view.eareacode.text = "0${item.areaCode}"
            var fire = ""
            for (f in item.fire){
                fire += ", 0$f"
            }
            view.efire.text = fire

            var electricity = ""
            for (e in item.electricity){
                electricity += ", 0$e"
            }
            view.eelectricity.text = electricity

            var police = ""
            for (p in item.police){
                police += ", 0$p"
            }
            view.epolice.text = police

            var hospital = ""
            for (p in item.hospital){
                hospital += ", 0$p"
            }
            view.ehospital.text = hospital

            var ambulance = ""
            for (p in item.ambulance){
                ambulance += ", 0$p"
            }
            view.eambulance.text = ambulance
        }



    }
}