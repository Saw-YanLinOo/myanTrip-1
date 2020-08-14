package com.vmyan.myantrip.ui.adapter


import android.content.Context
import android.content.Intent
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SnapHelper
import com.github.rubensousa.gravitysnaphelper.GravitySnapHelper
import com.vmyan.myantrip.R
import com.vmyan.myantrip.model.PlaceByCategory
import com.vmyan.myantrip.ui.PlaceDetailsActivity
import kotlinx.android.synthetic.main.place_category_item_layout.view.*

class PCSubCategoryListAdapter(private val c: Context, private val items: MutableList<PlaceByCategory>)
    : RecyclerView.Adapter<PCSubCategoryListAdapter.PCSubCategoryViewHolder>() {

    fun setItems(items: List<PlaceByCategory>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PCSubCategoryViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.place_category_item_layout,parent,false)
        return PCSubCategoryViewHolder(this.c, view)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: PCSubCategoryViewHolder, position: Int) {
        holder.bind(items[position])
    }


    class PCSubCategoryViewHolder( private val c: Context, private val view: View)
        :RecyclerView.ViewHolder(view),PCPlaceListAdapter.ItemClickListener {

        private val pcPlaceListAdapter = PCPlaceListAdapter(this, mutableListOf())

        fun bind(item: PlaceByCategory){
            view.pc_sub_cat_name.text = item.subPlaceCategory.name
            pcPlaceListAdapter.setItems(item.placeList)
            setUpPlaceRecycler(view)
        }

        private fun setUpPlaceRecycler(view: View){
            view.pc_place_list_recycler.layoutManager = LinearLayoutManager(view.context,
                LinearLayoutManager.HORIZONTAL,false)
            view.pc_place_list_recycler.apply {
                setHasFixedSize(true)
                setItemViewCacheSize(20)
            }

            val snapHelperStart: SnapHelper = GravitySnapHelper(Gravity.START)
            snapHelperStart.attachToRecyclerView(view.pc_place_list_recycler)
            view.pc_place_list_recycler.isNestedScrollingEnabled = false
            view.pc_place_list_recycler.adapter = pcPlaceListAdapter
        }


        override fun onPlaceClick(place_id: String) {
            val intent = Intent(c, PlaceDetailsActivity::class.java)
            intent.putExtra("place_id",place_id)
            c.startActivity(intent)

        }

    }
}