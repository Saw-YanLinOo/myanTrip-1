package com.vmyan.myantrip.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.vmyan.myantrip.R
import com.vmyan.myantrip.model.bookingCate.TownListItem
import kotlinx.android.synthetic.main.town_namelist_layout.view.*
import java.util.*
import kotlin.collections.ArrayList

class TownListAdapter(private val listener: ItemClickListener, private val cityList: MutableList<TownListItem>): RecyclerView.Adapter<TownListAdapter.TownViewHolder>(){

    interface ItemClickListener{
        fun onItemClick(cityName : String,cityImage : String)
    }



    fun setItems(items: List<TownListItem>){
        this.cityList.clear()
        this.cityList.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TownViewHolder {
        val view= LayoutInflater.from(parent.context).inflate(R.layout.town_namelist_layout,parent,false)
        return TownListAdapter.TownViewHolder(view, listener)
    }

    override fun getItemCount(): Int {
        return cityList.size
    }

    override fun onBindViewHolder(holder: TownViewHolder, position: Int) {
       holder.bind(cityList[position])
    }

    class TownViewHolder(private val view: View, private val listener: ItemClickListener):RecyclerView.ViewHolder(view),View.OnClickListener {
        private lateinit var townItem: TownListItem
        init {
            view.setOnClickListener ( this )
        }

        fun bind(item : TownListItem){
            this.townItem=item
            println(item)
            view.appComTxtTownName.text=item.townName
            Glide.with(view)
                .load(item.townImage)
                .into(view.appComImgCity)
        }

        override fun onClick(p0: View?) {
                listener.onItemClick(townItem.townName,townItem.townImage)


        }

    }



}