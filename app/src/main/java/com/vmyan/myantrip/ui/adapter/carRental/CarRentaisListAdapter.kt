package com.vmyan.myantrip.ui.adapter.carRental

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.vmyan.myantrip.R
import com.vmyan.myantrip.model.carRental.CarRentailsItem
import kotlinx.android.synthetic.main.car_rentals_list_item.view.*

class CarRentaisListAdapter (private val listener: ItemClickListener, private val items: MutableList<CarRentailsItem>):
    RecyclerView.Adapter<CarRentaisListAdapter.CarRentalsListViewHolder>() {
    interface ItemClickListener {
        fun onItemClick(id: String)
    }

    fun setItems(items: List<CarRentailsItem>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CarRentaisListAdapter.CarRentalsListViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.car_rentals_list_item, parent, false)
        return CarRentaisListAdapter.CarRentalsListViewHolder(view, listener)
    }

    override fun getItemCount(): Int {
       return items.size
    }

    override fun onBindViewHolder(
        holder: CarRentalsListViewHolder,
        position: Int
    ) {
        holder.bind(items[position])
    }

    class CarRentalsListViewHolder (
        private val view: View,
        private val listener: ItemClickListener
    ): RecyclerView.ViewHolder(view),View.OnClickListener
    {
        private lateinit var carList: CarRentailsItem
        init {
            view.setOnClickListener(this)
        }
        fun bind(item : CarRentailsItem){
            this.carList=item
            view.txt_carCompanyName.text=item.carName
            view.txt_NoOfPeople.text =item.noOfPeople
            view.txt_NoOfBag.text=item.noOfBag
            view.txt_carPrice.text=item.pricePerDay
            Glide.with(view).load(item.carCompanyLogo).into(view.img_carCompanyLogo)
            Glide.with(view).load(item.carImage).into(view.img_carImage)
        }

        override fun onClick(p0: View?) {
            listener.onItemClick(carList.carId)
        }

    }

}