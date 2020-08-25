package com.vmyan.myantrip.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.vmyan.myantrip.R
import com.vmyan.myantrip.model.Comments

class CommentAdapter(private val listener : CommentAdapter.onClickListener,private val comment : MutableList<Comments>) : RecyclerView.Adapter<CommentAdapter.CommentViewHolder>() {

    interface onClickListener {
        fun onClick()
    }

    fun setItems(comment : List<Comments>){
        this.comment.clear()
        this.comment.addAll(comment)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentViewHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.comment_recycler_adapter,parent,false)
        return CommentViewHolder(view,listener)
    }

    override fun onBindViewHolder(holder: CommentViewHolder, position: Int) {
        holder.bind(comment[position])
    }

    override fun getItemCount(): Int {
        return comment.size
    }

    class CommentViewHolder(private var view:View,private var listener: onClickListener) : RecyclerView.ViewHolder(view),View.OnClickListener {

        init {
            itemView.setOnClickListener(this)
        }
       fun  bind(comment : Comments) {

       }
        override fun onClick(p0: View?) {
            listener.onClick()
        }

    }
}