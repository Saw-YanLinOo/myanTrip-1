package com.vmyan.myantrip.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.vmyan.myantrip.R
import com.vmyan.myantrip.model.Word
import com.vmyan.myantrip.model.WordCategory
import kotlinx.android.synthetic.main.activity_add_new_trip.view.*
import kotlinx.android.synthetic.main.commu_category_recycler.view.*

class CommCategoryAdapter(private val listener: CommCategoryAdapter.ItemClickListener,private val title : MutableList<WordCategory>) : RecyclerView.Adapter<CommCategoryAdapter.CategoryViewHolder>(){

    interface ItemClickListener{
        fun onTitleClick(category: WordCategory)
    }

    fun setItems(postList: List<WordCategory>){
        this.title.clear()
        this.title.addAll(postList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.commu_category_recycler,parent,false)
        return CategoryViewHolder(view,listener)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.bind(title[position])
    }

    override fun getItemCount(): Int {
        return title.size
    }

    class CategoryViewHolder(private val view : View, private val listener : ItemClickListener) : RecyclerView.ViewHolder(view),View.OnClickListener {

        lateinit var category : WordCategory
        init {
            itemView.setOnClickListener(this)
            itemView.btn_title.setOnClickListener(this)
        }
        fun bind(category : WordCategory){
            this.category = category
            itemView.btn_title.text = category.category
        }

        override fun onClick(p0: View?) {
            listener.onTitleClick(category)
        }
    }
}