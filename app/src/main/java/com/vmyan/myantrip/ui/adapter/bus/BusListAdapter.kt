package com.vmyan.myantrip.ui.adapter.bus

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.vmyan.myantrip.R
import com.vmyan.myantrip.model.bus.BusListItem
import kotlinx.android.synthetic.main.bus_list_item.view.*
import kotlinx.android.synthetic.main.car_rentals_list_item.view.*
import kotlinx.android.synthetic.main.plan_bus_item_layout.view.*

class BusListAdapter (private val listener: ItemClickListener, private val items: MutableList<BusListItem>):
    RecyclerView.Adapter<BusListAdapter.BusListViewHolder>(){
    interface ItemClickListener {
        fun onItemClick(id: String)
    }

    fun setItems(items: List<BusListItem>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BusListAdapter.BusListViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.bus_list_item, parent, false)
        return BusListAdapter.BusListViewHolder(view, listener)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(
        holder: BusListViewHolder,
        position: Int
    ) {
        holder.bind(items[position])
    }

    class BusListViewHolder (
        private val view: View,
        private val listener: ItemClickListener
    ): RecyclerView.ViewHolder(view), View.OnClickListener
    {
        private lateinit var busList: BusListItem
        init {
            view.setOnClickListener(this)
        }
        fun bind(item : BusListItem){
            this.busList=item
            view.txt_BusName.text=item.busName
           view.txt_busOneSeatPrice.text=item.busPricePerSeat.toString()
            view.busTimeStart.text=item.busTimeStart
            view.busTimeStop.text=item.busTimeStop
            view.busFromTo.text=item.busFromTo
            view.busStatus.text=item.busStatus

            Glide.with(view).load(item.busImage).into(view.img_BusLogo)
        }

        override fun onClick(p0: View?) {
            listener.onItemClick(busList.busId)
        }

    }
}
