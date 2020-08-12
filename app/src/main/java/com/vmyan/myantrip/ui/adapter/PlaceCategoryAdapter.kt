package com.vmyan.myantrip.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.vmyan.myantrip.R
import com.vmyan.myantrip.model.PlaceCategory
import kotlinx.android.synthetic.main.home_cat_item.view.*

class PlaceCategoryAdapter(private val listener: ItemClickListener, private val items: MutableList<PlaceCategory>) : RecyclerView.Adapter<PlaceCategoryAdapter.PlaceCategoryViewHolder>() {
    interface ItemClickListener {
        fun onItemClick(cat_id: String, cat_name: String)
    }


    fun setItems(items: List<PlaceCategory>){
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaceCategoryViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.home_cat_item,parent,false)
        return PlaceCategoryViewHolder(view, listener)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: PlaceCategoryViewHolder, position: Int) {
        holder.bind(items[position])
    }


    class PlaceCategoryViewHolder(private val view: View, private val listener: ItemClickListener)
        :RecyclerView.ViewHolder(view), View.OnClickListener {

        private lateinit var placeCategory: PlaceCategory

        init {
            view.setOnClickListener(this)
        }

        fun bind(item: PlaceCategory){
            this.placeCategory = item
            view.cat_name.text = item.cat_name
            Glide.with(view)
                .load(item.cat_img_url)
                .into(view.cat_img)
        }

        override fun onClick(p0: View?) {
            listener.onItemClick(placeCategory.cat_id, placeCategory.cat_name)
        }

    }
}