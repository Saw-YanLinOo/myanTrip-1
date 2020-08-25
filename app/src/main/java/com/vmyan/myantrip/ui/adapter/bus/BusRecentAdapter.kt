package com.vmyan.myantrip.ui.adapter.bus

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.vmyan.myantrip.R
import com.vmyan.myantrip.model.bus.BusRecentItem
import kotlinx.android.synthetic.main.bus_recent_item.view.*

class BusRecentAdapter internal constructor(context: Context):
    RecyclerView.Adapter<BusRecentAdapter.BusRecentViewHolder>(){
    private  val inflate: LayoutInflater = LayoutInflater.from(context)
    private var items= emptyList<BusRecentItem>()

    inner class BusRecentViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        private lateinit var busRecentItem : BusRecentItem

        fun bind(items : BusRecentItem){
            this.busRecentItem=items

            itemView.busRecentFrom.text=items.busFrom
            itemView.busRecentTo.text=items.busTo
            itemView.busRecentDepartDate.text=items.busDepartDate

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BusRecentViewHolder {
        val itemView=inflate.inflate(R.layout.bus_recent_item,parent,false)
        return BusRecentViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: BusRecentViewHolder, position: Int) {
        holder.bind(items[position])

    }

    internal fun setWords(items: List<BusRecentItem>) {
        this.items = items

        notifyDataSetChanged()
    }

}