package com.vmyan.myantrip.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.vmyan.myantrip.R
import com.vmyan.myantrip.model.Place
import kotlinx.android.synthetic.main.layout_slider_card.view.*
import kotlinx.android.synthetic.main.pc_place_item_layout.view.*

class PlaceDetailsGalleryAdapter( private val items: ArrayList<String>) : RecyclerView.Adapter<PlaceDetailsGalleryAdapter.PlaceDetailsGalleryViewHolder>() {


    fun setItems(items: List<String>){
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaceDetailsGalleryViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.layout_slider_card,parent,false)
        return PlaceDetailsGalleryViewHolder(view)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: PlaceDetailsGalleryViewHolder, position: Int) {
        holder.bind(items[position])
    }


    class PlaceDetailsGalleryViewHolder(private val view: View)
        :RecyclerView.ViewHolder(view) {

        @SuppressLint("SetTextI18n")
        fun bind(item: String){
            Glide.with(view)
                .load(item)
                .into(view.image)
        }



    }
}