package com.vmyan.myantrip.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.vmyan.myantrip.R
import com.vmyan.myantrip.model.Place
import kotlinx.android.synthetic.main.search_place_item_layout.view.*

class SearchPlaceListAdapter(private val listener: ItemClickListener, private val items: MutableList<Place>) : RecyclerView.Adapter<SearchPlaceListAdapter.SearchPlaceListViewHolder>() {
    interface ItemClickListener {
        fun onPlaceClick(place_id : String)
    }

    fun setItems(items: List<Place>){
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
        holder.bind(items[position])
    }


    class SearchPlaceListViewHolder(private val view: View, private val listener: ItemClickListener)
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
                .into(view.s_img)
            view.s_name.text = item.name
            view.s_address.text = item.address + ", " + item.city + ", " + item.state + ", "+ item.country
            view.s_rval.text = item.ratingValue.toString()
            view.s_category.text = item.category
        }

        override fun onClick(p0: View?) {
            listener.onPlaceClick(place.place_id)
        }

    }


}