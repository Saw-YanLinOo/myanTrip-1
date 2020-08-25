package com.vmyan.myantrip.ui.adapter.train

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.vmyan.myantrip.R
import com.vmyan.myantrip.model.train.TrainListItem
import kotlinx.android.synthetic.main.train_list_item.view.*

class TrainListAdapter (private val listener: ItemClickListener, private val items: MutableList<TrainListItem>):
    RecyclerView.Adapter<TrainListAdapter.TrainListViewHolder>() {
    interface ItemClickListener {
        fun onItemClick(id: String)
    }

    fun setItems(items: List<TrainListItem>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TrainListAdapter.TrainListViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.train_list_item, parent, false)
        return TrainListAdapter.TrainListViewHolder(view, listener)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(
        holder: TrainListViewHolder,
        position: Int
    ) {
        holder.bind(items[position])
    }

    class TrainListViewHolder(
        private val view: View,
        private val listener: ItemClickListener
    ) : RecyclerView.ViewHolder(view), View.OnClickListener {
        private lateinit var trainList: TrainListItem

        init {
            view.setOnClickListener(this)
        }

        fun bind(item: TrainListItem) {
            this.trainList = item
            view.txt_TrainName.text = item.tranNo
            view.txt_trainOneSeatPrice.text=item.trainPerSeatPrice.toString()
            view.trainTimeStart.text=item.trainTimeStart
            view.trainTimeStop.text=item.trainTimeStop
            view.trainFromTo.text=item.trainFromTo
            //  view.txt_busOneSeatPrice.text=item.busPricePerSeat

              Glide.with(view).load(item.trainImage).into(view.img_trainImage)
            //  Glide.with(view).load(item.carImage).into(view.img_carImage)
        }

        override fun onClick(p0: View?) {
            listener.onItemClick(trainList.trainId)
        }

    }
}