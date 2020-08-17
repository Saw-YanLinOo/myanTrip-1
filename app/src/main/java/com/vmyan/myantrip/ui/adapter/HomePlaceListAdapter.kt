package com.vmyan.myantrip.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.vmyan.myantrip.R
import com.vmyan.myantrip.model.Place
import kotlinx.android.synthetic.main.home_place_list_item.view.*
import java.text.DecimalFormat

class HomePlaceListAdapter(private val listener: ItemClickListener, private val items: MutableList<Place>) : RecyclerView.Adapter<HomePlaceListAdapter.HomePlaceViewHolder>() {
    interface ItemClickListener {
        fun onPlaceClick(place_id : String)
    }


    fun setItems(items: List<Place>){
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomePlaceViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.home_place_list_item,parent,false)
        return HomePlaceViewHolder(view, listener)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: HomePlaceViewHolder, position: Int) {
        holder.bind(items[position])
    }


    class HomePlaceViewHolder(private val view: View, private val listener: ItemClickListener)
        :RecyclerView.ViewHolder(view), View.OnClickListener {

        private lateinit var place: Place

        init {
            view.setOnClickListener(this)
        }

        @SuppressLint("SetTextI18n")
        fun bind(item: Place){
            this.place = item
            Glide.with(view)
                .load(item.mainImg)
                .into(view.image)
            view.name.text = item.name
            view.category.text = item.category
            view.address.text = item.address + ", " + item.city + ", " + item.state + ", "+ item.country
            view.rating_value.text = DecimalFormat("#.#").format(item.ratingValue).toString()
            view.rating_bar.rating = item.ratingValue
        }

        override fun onClick(p0: View?) {
            listener.onPlaceClick(place.place_id)
        }

    }
}