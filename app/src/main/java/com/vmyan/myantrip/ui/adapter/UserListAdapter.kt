package com.vmyan.myantrip.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.vmyan.myantrip.R
import com.vmyan.myantrip.model.User
import kotlinx.android.synthetic.main.blog_user_recycler_view.view.*

class UserListAdapter(private val listener: UserListAdapter.ItemClickListener, private val userList :MutableList<User>) :RecyclerView.Adapter<UserListAdapter.UserViewHolder>() {
    interface ItemClickListener {
        fun onItemClickListener(user: User)
    }

    fun setItems(userList : List<User>){
        this.userList.clear()
        this.userList.addAll(userList)
        notifyDataSetChanged()

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.blog_user_recycler_view,parent,false)
        return UserViewHolder(view,listener)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(userList[position])
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    class UserViewHolder(view: View,var listener: UserListAdapter.ItemClickListener) : RecyclerView.ViewHolder(view),View.OnClickListener{

        private lateinit var user: User
        init {
            itemView.setOnClickListener(this)

        }

        fun bind(user: User){
            this.user = user
            Glide.with(itemView)
                .load(user.profilephoto)
                .placeholder(R.drawable.profile)
                .transform(CircleCrop())
                .into(itemView.user_profile_u)
            itemView.user_name_u.text = user.username

        }

        override fun onClick(p0: View?) {
            listener.onItemClickListener(user)
        }

    }
}