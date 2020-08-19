package com.vmyan.myantrip.ui.adapter

import android.annotation.SuppressLint
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.google.firebase.Timestamp
import com.smarteist.autoimageslider.SliderAnimations
import com.vmyan.myantrip.R
import com.vmyan.myantrip.model.GetPost
import com.vmyan.myantrip.ui.Profile
import kotlinx.android.synthetic.main.post_recyclerviews.view.*

class PostListAdapter(private val listener: PostListAdapter.ItemClickListener,private val postList:MutableList<GetPost>) : RecyclerView.Adapter<PostListAdapter.BlogViewHolder>(){

    interface ItemClickListener {
        fun onPostClick(position : Int)
    }
    fun setItems(postList: List<GetPost>){
        this.postList.clear()
        this.postList.addAll(postList)
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BlogViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.post_recyclerviews,parent,false)
        return BlogViewHolder(view,listener)
    }

    override fun getItemCount(): Int {
        return postList.size
    }

    override fun onBindViewHolder(holder: BlogViewHolder, position: Int) {
        holder.bind(postList[position])
    }
    class BlogViewHolder(private val view: View, private val listener: ItemClickListener): RecyclerView.ViewHolder(view),View.OnClickListener{

        private  val imageSliderAdapter = PostImageSliderAdapter()
        private lateinit var posts : GetPost

        init {
            view.setOnClickListener(this)
            view.btn_comment.setOnClickListener(this)
        }
        @SuppressLint("SetTextI18n")
        fun bind(post: GetPost){
            this.posts = post
            Glide.with(itemView) //1
                .load(post.user.profilephoto)
                .placeholder(R.drawable.ic_account_circle)
                .error(R.drawable.ic_account_circle)
                .skipMemoryCache(true) //2
                .diskCacheStrategy(DiskCacheStrategy.NONE) //3
                .transform(CircleCrop()) //4
                .into(itemView.img_profile)

            itemView.tv_username.text = post.user.username
            itemView.tv_time.text = timeInterval(post.posts.time, Timestamp.now()) + " ago"
            itemView.tv_descripton.text = post.posts.description
            itemView.tv_Like.text = post.posts.like

            Log.e("", post.posts.image_url.toString())
            imageSliderAdapter.setItems(post.posts.image_url)
            itemView.imageSlider.setSliderAdapter(imageSliderAdapter)
            itemView.imageSlider.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
            itemView.imageSlider.setScrollTimeInSec(1000);

            itemView.img_profile.setOnClickListener{
                val intent = Intent(view.context, Profile::class.java)
                intent.putExtra("user_id",post.user.user_id)
                view.context.startActivity(intent)

            }
            itemView.btn_like.setOnClickListener{
                println("You Like ${posts.posts.id}")
            }
            itemView.btn_follow.setOnClickListener{
                println("You Follow ${posts.user.username}")
            }
            itemView.tv_descripton.setOnClickListener{
                    itemView.tv_descripton.setSingleLine(false)
                    itemView.tv_descripton.ellipsize = null;
            }
        }
        fun timeInterval (start : Timestamp,now : Timestamp) : String{
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
            listener.onPostClick(adapterPosition)
        }
    }
}