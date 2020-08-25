package com.vmyan.myantrip.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.vmyan.myantrip.R
import com.vmyan.myantrip.model.Place
import com.vmyan.myantrip.model.PlaceDetails
import kotlinx.android.synthetic.main.search_place_item_layout.view.*
import java.text.DecimalFormat

class SearchPlaceListAdapter(private val listener: ItemClickListener, private val items: MutableList<PlaceDetails>) : RecyclerView.Adapter<SearchPlaceListAdapter.SearchPlaceListViewHolder>() {
    interface ItemClickListener {
        fun onPlaceClick(place : PlaceDetails)
    }

    fun setItems(items: List<PlaceDetails>){
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchPlaceListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.search_place_item_layout,parent,false)
        return SearchPlaceListViewHolder(view, listener)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: SearchPlaceListViewHolder, position: Int) {
        holder.bind(items[position], position)
    }


    class SearchPlaceListViewHolder(private val view: View, private val listener: ItemClickListener)
        :RecyclerView.ViewHolder(view), View.OnClickListener {

        private lateinit var place: PlaceDetails
        private var p = 0

        init {
            view.setOnClickListener(this)
        }

        @SuppressLint("SetTextI18n")
        fun bind(item: PlaceDetails,pos : Int){
            this.p = pos
            this.place = item
            Glide.with(view)
                .load(item.place.mainImg)
                .into(view.s_img)
            view.s_name.text = item.place.name
            view.s_address.text = item.place.address + ", " + item.place.city + ", " + item.place.state + ", "+ item.place.country
            view.s_rval.text = DecimalFormat("#.#").format(item.place.ratingValue).toString()
            view.s_category.text = item.place.category
        }

        override fun onClick(p0: View?) {
            listener.onPlaceClick(this.place)
        }

    }


}