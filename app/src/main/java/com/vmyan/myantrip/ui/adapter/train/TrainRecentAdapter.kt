package com.vmyan.myantrip.ui.adapter.train

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.vmyan.myantrip.R
import com.vmyan.myantrip.model.train.TrainRecentItem
import kotlinx.android.synthetic.main.hotel_recent_item.view.*
import kotlinx.android.synthetic.main.train_recent_item.view.*

class TrainRecentAdapter internal constructor(context: Context):
    RecyclerView.Adapter<TrainRecentAdapter.TrainRecentViewHolder>() {
    private val inflate: LayoutInflater = LayoutInflater.from(context)
    private var items = emptyList<TrainRecentItem>()

    inner class TrainRecentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private lateinit var hotelRecentItem: TrainRecentItem

        fun bind(items: TrainRecentItem) {
            this.hotelRecentItem = items
            itemView.trainRecentFrom.text = items.trainFrom
            itemView.trainRecentTo.text = items.trainTo
            itemView.train_RecentDepartDate.text = items.trainDepartDate
            itemView.txtTrainNoOfPeople.text=items.trainNoOfPeople
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrainRecentViewHolder {
        val itemView = inflate.inflate(R.layout.train_recent_item, parent, false)
        return TrainRecentViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: TrainRecentViewHolder, position: Int) {
        holder.bind(items[position])

    }

    internal fun setWords(items: List<TrainRecentItem>) {
        this.items = items

        notifyDataSetChanged()
    }
}
