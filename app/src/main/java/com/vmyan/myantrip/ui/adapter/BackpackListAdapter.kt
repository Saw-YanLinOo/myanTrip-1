package com.vmyan.myantrip.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.vmyan.myantrip.R
import com.vmyan.myantrip.model.Backpack
import com.vmyan.myantrip.model.PlaceCategory
import kotlinx.android.synthetic.main.backpack_item_layout.view.*
import kotlinx.android.synthetic.main.home_cat_item.view.*

class BackpackListAdapter(private val listener: ItemClickListener, private val items: MutableList<Backpack>) : RecyclerView.Adapter<BackpackListAdapter.BackpackListViewHolder>() {
    interface ItemClickListener {
        fun onItemClick(id: String)
    }


    fun setItems(items: List<Backpack>){
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BackpackListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.backpack_item_layout,parent,false)
        return BackpackListViewHolder(view, listener)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: BackpackListViewHolder, position: Int) {
        holder.bind(items[position])
    }


    class BackpackListViewHolder(private val view: View, private val listener: ItemClickListener)
        :RecyclerView.ViewHolder(view) {

        private lateinit var backpack: Backpack

        fun bind(item: Backpack){
            this.backpack = item
            view.bitemname.text = item.name
            view.bcount.text = item.count.toString()
            view.bcheck.isChecked = item.status
            view.bcheck.isClickable = !item.status

            view.bcheck.setOnClickListener {
                listener.onItemClick(backpack.bid)
            }
        }

    }
}