package com.vmyan.myantrip.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.google.firebase.Timestamp
import com.vmyan.myantrip.R
import com.vmyan.myantrip.model.Comments
import kotlinx.android.synthetic.main.comment_recycler_adapter.view.*

class CommentListAdapter(private val listener : CommentListAdapter.ItemClickListener, private var commentList : MutableList<Comments>) : RecyclerView.Adapter<CommentListAdapter.CommentViewHolder>() {
    interface ItemClickListener {
        fun onItemClick(comments: Comments)
    }

    fun setItems(commentList : List<Comments>){
        this.commentList.clear()
        this.commentList.addAll(commentList)
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.comment_recycler_adapter,parent,false)
        return CommentViewHolder(view,listener)
    }

    override fun onBindViewHolder(holder: CommentViewHolder, position: Int) {
        holder.bind(commentList[position])
    }

    override fun getItemCount(): Int {
        return commentList.size
    }

    class CommentViewHolder(view : View,private val listener: CommentListAdapter.ItemClickListener) : RecyclerView.ViewHolder(view), View.OnClickListener {

        private lateinit var comments : Comments

        init {
            itemView.setOnClickListener(this)
        }

        fun bind(comments: Comments){
            this.comments = comments
            Glide.with(itemView) //1
                .load(comments.user.profilephoto)
                .placeholder(R.drawable.ic_account_circle)
                .transform(CircleCrop()) //4
                .into(itemView.img_profile_c)
            itemView.tv_username_c.text = comments.user.username
            itemView.tv_descripton_c.text = comments.description
            itemView.tv_time_c.text = timeInterval(comments.time!!, Timestamp.now())
        }

        fun timeInterval (start : Timestamp, now : Timestamp) : String{
            val diff = now.seconds - start.seconds
            val day = diff/86400
            val leftsec = diff%86400
            val hour = leftsec/3600
            val leftsec2 = leftsec%3600
            val min = leftsec2/60
            val sec = leftsec2%60
            if (day > 0){
                return day.toString()+"day"
            }else if (hour > 0){
                return hour.toString()+"hour"
            }else if (min > 0){
                return min.toString()+"min"
            }else {
                return sec.toString()+"sec"
            }
        }
        override fun onClick(p0: View?) {
            listener.onItemClick(comments)
        }

    }

}