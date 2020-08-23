package com.vmyan.myantrip.ui.adapter


import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.vmyan.myantrip.R
import com.vmyan.myantrip.model.Review
import com.vmyan.myantrip.model.User
import kotlinx.android.synthetic.main.review_item_layout.view.*
import kotlinx.android.synthetic.main.trip_plan_users_item_layout.view.*
import java.text.SimpleDateFormat

class TripPlanUserListAdapter(private val items: MutableList<User>)
    : RecyclerView.Adapter<TripPlanUserListAdapter.TripPlanListViewHolder>() {

    fun setItems(items: List<User>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TripPlanListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.trip_plan_users_item_layout,parent,false)
        return TripPlanListViewHolder(view)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: TripPlanListViewHolder, position: Int) {
        holder.bind(items[position])
    }


    class TripPlanListViewHolder( private val view: View)
        :RecyclerView.ViewHolder(view) {


        @SuppressLint("SimpleDateFormat")
        fun bind(item: User){
            view.userName.text = item.username
            view.userEmail.text = item.email
            Glide.with(view)
                .load(item.profilephoto)
                .into(view.userimg)
        }



    }
}