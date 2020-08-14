package com.vmyan.myantrip.ui.adapter


import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.vmyan.myantrip.R
import com.vmyan.myantrip.model.Review
import kotlinx.android.synthetic.main.review_item_layout.view.*
import java.text.SimpleDateFormat

class ReviewListAdapter(private val items: MutableList<Review>)
    : RecyclerView.Adapter<ReviewListAdapter.ReviewListViewHolder>() {

    fun setItems(items: List<Review>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.review_item_layout,parent,false)
        return ReviewListViewHolder(view)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ReviewListViewHolder, position: Int) {
        holder.bind(items[position])
    }


    class ReviewListViewHolder( private val view: View)
        :RecyclerView.ViewHolder(view) {


        @SuppressLint("SimpleDateFormat")
        fun bind(item: Review){
            val date = item.date.toDate()
            val pattern = "dd/MM/yyyy"
            val simpleDateFormat = SimpleDateFormat(pattern)
            val d = simpleDateFormat.format(date)
            view.review_username.text = item.user_name
            view.review_desc.text = item.desc
            view.review_userratingbar.rating = item.rating_val
            view.review_date.text = d
            Glide.with(view)
                .load(item.user_img)
                .into(view.review_userimg)
        }



    }
}