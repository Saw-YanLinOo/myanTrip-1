package com.vmyan.myantrip.ui.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.smarteist.autoimageslider.SliderAnimations
import com.vmyan.myantrip.R
import com.vmyan.myantrip.model.Posts
import kotlinx.android.synthetic.main.post_recyclerviews.view.*

class PostListAdapter(private val listener: PostListAdapter.ItemClickListener,private val postList:MutableList<Posts>) : RecyclerView.Adapter<PostListAdapter.BlogViewHolder>(){

    interface ItemClickListener {
        fun onPostClick(position : Int)
    }
    fun setItems(postList: List<Posts>){
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
        private lateinit var posts : Posts

        init {
            view.setOnClickListener(this)
            view.btn_comment.setOnClickListener(this)
        }
        fun bind( post: Posts){
            this.posts = post
//            Glide.with(itemView) //1
//                .load(post.profile)
//                .placeholder(R.drawable.profile)
//                .skipMemoryCache(true) //2
//                .diskCacheStrategy(DiskCacheStrategy.NONE) //3
//                .transform(CircleCrop()) //4
//                .into(itemView.img_profile)

//            itemView.tv_username.text = post.username
            itemView.tv_descripton.text = post.description
            itemView.tv_Like.text = post.like

            Log.e("", post.image_url.toString())
            imageSliderAdapter.setItems(post.image_url)
            itemView.imageSlider.setSliderAdapter(imageSliderAdapter)
            itemView.imageSlider.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
            itemView.imageSlider.setScrollTimeInSec(1000);

            itemView.img_profile.setOnClickListener{
//                val intent = Intent(view.context, Profile::class.java)
//                intent.putExtra("user_id",post.user_id)
//                view.context.startActivity(intent)

            }
            itemView.btn_like.setOnClickListener{
                println("You Like ${posts.id}")
            }
            itemView.btn_follow.setOnClickListener{
                println("You Follow ${posts.id}")
            }
        }

        override fun onClick(p0: View?) {
            listener.onPostClick(adapterPosition)
        }
    }
}