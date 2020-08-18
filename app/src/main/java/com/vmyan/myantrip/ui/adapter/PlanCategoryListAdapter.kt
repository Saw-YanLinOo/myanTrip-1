package com.vmyan.myantrip.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.vmyan.myantrip.R
import com.vmyan.myantrip.model.Place
import com.vmyan.myantrip.model.PlanCategory
import kotlinx.android.synthetic.main.plan_category_layout_item.view.*
import kotlinx.android.synthetic.main.search_place_item_layout.view.*
import java.text.DecimalFormat

class PlanCategoryListAdapter(private val listener: ItemClickListener, private val items: MutableList<PlanCategory>) : RecyclerView.Adapter<PlanCategoryListAdapter.PlanCategoryListViewHolder>() {
    interface ItemClickListener {
        fun onPlaceClick(name : String, img: String)
    }

    fun setItems(items: List<PlanCategory>){
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlanCategoryListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.plan_category_layout_item,parent,false)
        return PlanCategoryListViewHolder(view, listener)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: PlanCategoryListViewHolder, position: Int) {
        holder.bind(items[position])
    }


    class PlanCategoryListViewHolder(private val view: View, private val listener: ItemClickListener)
        :RecyclerView.ViewHolder(view), View.OnClickListener {

        private lateinit var plan: PlanCategory

        init {
            view.setOnClickListener(this)
        }

        @SuppressLint("SetTextI18n")
        fun bind(item: PlanCategory){
            this.plan = item
            view.plancat_name.text = item.name
            Glide.with(view)
                .load(item.img)
                .into(view.plancat_img)
        }

        override fun onClick(p0: View?) {
            listener.onPlaceClick(plan.name, plan.img)
        }

    }


}