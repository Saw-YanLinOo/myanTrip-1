package com.vmyan.myantrip.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.vmyan.myantrip.R
import com.vmyan.myantrip.model.Place
import kotlinx.android.synthetic.main.pc_place_item_layout.view.*
import java.text.DecimalFormat

class PCPlaceListAdapter(private val listener: ItemClickListener, private val items: MutableList<Place>) : RecyclerView.Adapter<PCPlaceListAdapter.PCPlaceViewHolder>() {
    interface ItemClickListener {
        fun onPlaceClick(place_id : String)
    }


    fun setItems(items: List<Place>){
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PCPlaceViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.pc_place_item_layout,parent,false)
        return PCPlaceViewHolder(view, listener)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: PCPlaceViewHolder, position: Int) {
        holder.bind(items[position])
    }


    class PCPlaceViewHolder(private val view: View, private val listener: ItemClickListener)
        :RecyclerView.ViewHolder(view), View.OnClickListener {

        private lateinit var place: Place

        init {
            view.setOnClickListener(this)
        }

        @SuppressLint("SetTextI18n")
        fun bind(item: Place){
            this.place = item
            view.pc_place_name.text = item.name
            view.pc_rating_val.text = DecimalFormat("#.#").format(item.ratingValue).toString()
            Glide.with(view)
                .load(item.mainImg)
                .into(view.pc_img)
        }

        override fun onClick(p0: View?) {
            listener.onPlaceClick(place.place_id)
        }

    }
}