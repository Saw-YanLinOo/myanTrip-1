package com.vmyan.myantrip.ui.adapter.carRental

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.vmyan.myantrip.R
import com.vmyan.myantrip.model.carRental.CarRentalRecentItem
import kotlinx.android.synthetic.main.car_rental_recent_item.view.*

class CarRecentAdapter  internal constructor(context: Context):
    RecyclerView.Adapter<CarRecentAdapter.CarRecentViewHolder>() {
    private val inflate: LayoutInflater = LayoutInflater.from(context)
    private var items = emptyList<CarRentalRecentItem>()

    inner class CarRecentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private lateinit var carRecentItem: CarRentalRecentItem

        fun bind(items: CarRentalRecentItem) {
            this.carRecentItem = items
            Glide.with(itemView)
                .load(items.cityImage)
                .into(itemView.carRentalImgCity_recent)
            itemView.carRentalTxtTownNameSel.text = items.townName
            itemView.txt_recentCarRentalSdate.text = items.sDate
            itemView.txt_recentCarRentalLdate.text = items.lDate
            itemView.txtCarRentalNoOfPeople.text = items.noOfPeople
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarRecentViewHolder {
        val itemView = inflate.inflate(R.layout.car_rental_recent_item, parent, false)
        return CarRecentViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: CarRecentViewHolder, position: Int) {
        holder.bind(items[position])

    }

    internal fun setWords(items: List<CarRentalRecentItem>) {
        this.items = items

        notifyDataSetChanged()
    }
}