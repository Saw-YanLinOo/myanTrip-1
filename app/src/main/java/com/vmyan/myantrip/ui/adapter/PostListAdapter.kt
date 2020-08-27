package com.vmyan.myantrip.ui.adapter

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.FirebaseFirestore
import com.smarteist.autoimageslider.SliderAnimations
import com.vmyan.myantrip.R
import com.vmyan.myantrip.model.GetPost
import com.vmyan.myantrip.model.Place
import com.vmyan.myantrip.ui.CommentActivity
import com.vmyan.myantrip.ui.PlaceDetailsActivity
import com.vmyan.myantrip.ui.Profile
import com.vmyan.myantrip.utils.PostHelper
import com.vmyan.myantrip.utils.showToast
import kotlinx.android.synthetic.main.post_description_layout.view.*
import kotlinx.android.synthetic.main.post_place_layout.view.*
import kotlinx.android.synthetic.main.post_place_recyclerview.view.*
import kotlinx.android.synthetic.main.post_recyclerviews.view.btn_comment
import kotlinx.android.synthetic.main.post_recyclerviews.view.btn_follow
import kotlinx.android.synthetic.main.post_recyclerviews.view.btn_like
import kotlinx.android.synthetic.main.post_recyclerviews.view.imageSlider
import kotlinx.android.synthetic.main.post_recyclerviews.view.img_profile
import kotlinx.android.synthetic.main.post_recyclerviews.view.tv_Like
import kotlinx.android.synthetic.main.post_recyclerviews.view.tv_descripton
import kotlinx.android.synthetic.main.post_recyclerviews.view.tv_time
import kotlinx.android.synthetic.main.post_recyclerviews.view.tv_total_comment_view
import kotlinx.android.synthetic.main.post_recyclerviews.view.tv_total_unlike
import kotlinx.android.synthetic.main.post_recyclerviews.view.tv_username

class PostListAdapter(private val listener: ItemClickListener,private val postList:MutableList<GetPost>) : RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    private val firebaseFirestore = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()
    private var postHelper = PostHelper()
    interface ItemClickListener {
        fun onPostClick(position : Int)
    }

    companion object {
        const val DESCRIPTION = 1
        const val IMAGE = 2
        const val PLACE = 3
        const val PLACE_IMAGE = 4
    }
    fun setItems(postList: List<GetPost>){
        this.postList.clear()
        this.postList.addAll(postList)
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        when(viewType){
            DESCRIPTION -> {
                return BlogDescriptionViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.post_description_layout,parent,false),listener)
            }
            IMAGE -> {
                return  BlogImageViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.post_recyclerviews,parent,false),listener)
            }
            PLACE -> {
                return  BlogPlaceViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.post_place_recyclerview,parent,false),listener)
            }
            PLACE_IMAGE -> {
                return  BlogPlaceImageViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.post_place_layout,parent,false),listener)
            }
        }
        return BlogDescriptionViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.post_description_layout,parent,false),listener)
    }

    override fun getItemCount(): Int {
        return postList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holder.setIsRecyclable(false)
        when(postList[position].posts.type){
            DESCRIPTION ->  (holder as BlogDescriptionViewHolder).bind(postList[position])
            IMAGE -> (holder as BlogImageViewHolder).bind(postList[position])
            PLACE -> (holder as BlogPlaceViewHolder).bind(postList[position])
            PLACE_IMAGE -> (holder as BlogPlaceImageViewHolder).bind(postList)
        }
    }

    override fun getItemViewType(position: Int): Int {
        when(postList[position].posts.type){
            DESCRIPTION -> return 1
            IMAGE -> return 2
            PLACE -> return 3
            PLACE_IMAGE -> return 4
        }
        return 1
    }

    private inner class BlogDescriptionViewHolder(private val view: View, private val listener: ItemClickListener): RecyclerView.ViewHolder(view),View.OnClickListener{

        private lateinit var posts : GetPost

        init {
            view.setOnClickListener(this)
        }
        @SuppressLint("SetTextI18n")
        fun bind(post: GetPost){
            this.posts = post
            Glide.with(itemView) //1
                .load(post.user.profilephoto)
                .placeholder(R.drawable.ic_account_circle)
                .transform(CircleCrop()) //4
                .into(itemView.img_profile)

            itemView.tv_username.text = post.user.username
            itemView.tv_time.text = timeInterval(post.posts.time!!, Timestamp.now()) + " ago"
            itemView.tv_description.text = post.posts.description
            itemView.tv_Like.text = "${post.posts.like} like"
            itemView.tv_total_unlike.text = "${post.posts.unlike} unlike"
            itemView.tv_total_comment_view.text = "${post.posts.comments} comment"

            itemView.img_profile.setOnClickListener{
                val intent = Intent(view.context, Profile::class.java)
                intent.putExtra("user_id",post.user.user_id)
                intent.putExtra("user_name",post.user.username)
                intent.putExtra("user_profile",post.user.profilephoto)
                view.context.startActivity(intent)

            }
            itemView.btn_like.setOnClickListener{
                println("You Like ${posts.posts.id}")
            }
            itemView.btn_follow.setOnClickListener{
                println("You Follow ${posts.user.username}")
            }
            itemView.tv_description.setOnClickListener{
                itemView.tv_description.isSingleLine = false
                itemView.tv_description.ellipsize = null
            }
            itemView.btn_comment.setOnClickListener {
                var intent = Intent(itemView.context,CommentActivity::class.java)
                intent.putExtra("postId",posts.posts.id)
                intent.putExtra("postComment",posts.posts.comments!!.toInt())
                itemView.context.startActivity(intent)
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
    private inner class BlogImageViewHolder(private val view: View, private val listener: ItemClickListener): RecyclerView.ViewHolder(view),View.OnClickListener{
        private  val imageSliderAdapter = PostImageSliderAdapter()
        private lateinit var posts : GetPost

        init {
            view.setOnClickListener(this)
        }
        @SuppressLint("SetTextI18n")
        fun bind(post: GetPost){
            this.posts = post
            Glide.with(itemView) //1
                .load(post.user.profilephoto)
                .placeholder(R.drawable.ic_account_circle)
                .transform(CircleCrop()) //4
                .into(itemView.img_profile)

            itemView.tv_username.text = post.user.username
            itemView.tv_time.text = timeInterval(post.posts.time!!, Timestamp.now()) + " ago"
            itemView.tv_descripton.text = post.posts.description
            itemView.tv_Like.text = "${post.posts.like} like"
            itemView.tv_total_unlike.text = "${post.posts.unlike} unlike"
            itemView.tv_total_comment_view.text = "${post.posts.comments} comment"

            Log.e("", post.posts.image_url.toString())
            imageSliderAdapter.setItems(post.posts.image_url)
            itemView.imageSlider.setSliderAdapter(imageSliderAdapter)
            itemView.imageSlider.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION)
            itemView.imageSlider.scrollTimeInSec = 1000

            itemView.img_profile.setOnClickListener{
                val intent = Intent(view.context, Profile::class.java)
                intent.putExtra("user_id",post.user.user_id)
                intent.putExtra("user_name",post.user.username)
                intent.putExtra("user_profile",post.user.profilephoto)
                view.context.startActivity(intent)

            }
            itemView.btn_like.setOnClickListener{
                println("You Like ${posts.posts.id}")
            }
            itemView.btn_follow.setOnClickListener{
                println("You Follow ${posts.user.username}")
            }
            itemView.tv_descripton.setOnClickListener{
                    itemView.tv_descripton.isSingleLine = false
                    itemView.tv_descripton.ellipsize = null
            }
            itemView.btn_comment.setOnClickListener {
                var intent = Intent(itemView.context,CommentActivity::class.java)
                intent.putExtra("postId",posts.posts.id)
                intent.putExtra("postComment",posts.posts.comments!!.toInt())
                itemView.context.startActivity(intent)
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

    private inner class BlogPlaceViewHolder(private val view: View, private val listener: ItemClickListener): RecyclerView.ViewHolder(view),View.OnClickListener{
        private  val imageSliderAdapter = PostImageSliderAdapter()
        private lateinit var posts : GetPost

        init {
            view.setOnClickListener(this)
        }
        @SuppressLint("SetTextI18n")
        fun bind(post: GetPost){
            this.posts = post
            Glide.with(itemView)
                .load(post.user.profilephoto)
                .placeholder(R.drawable.ic_account_circle)
                .transform(CircleCrop())
                .into(itemView.img_profile)

            itemView.tv_username.text = post.user.username
            itemView.tv_time.text = timeInterval(post.posts.time!!, Timestamp.now()) + " ago"
            itemView.tv_descripton.text = post.posts.description
            itemView.tv_Like.text = "${post.posts.like} like"
            itemView.tv_total_unlike.text = "${post.posts.unlike} unlike"
            itemView.tv_total_comment_view.text = "${post.posts.comments} comment"

            itemView.tv_place_name_p.text = "${post.place!!.name} (${post.place!!.category})"
            itemView.tv_place_address_p.text = post.place!!.address

            Log.e("", post.place!!.gallery.toString())
            imageSliderAdapter.setItems(post.place!!.gallery)
            itemView.imageSlider.setSliderAdapter(imageSliderAdapter)
            itemView.imageSlider.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION)
            itemView.imageSlider.scrollTimeInSec = 1000

            itemView.img_profile.setOnClickListener{
                val intent = Intent(view.context, Profile::class.java)
                intent.putExtra("user_id",post.user.user_id)
                intent.putExtra("user_name",post.user.username)
                intent.putExtra("user_profile",post.user.profilephoto)
                view.context.startActivity(intent)

            }
            itemView.btn_like.setOnClickListener{
                println("You Like ${posts.posts.id}")
            }
            itemView.btn_follow.setOnClickListener{
                println("You Follow ${posts.user.username}")
            }
            itemView.tv_descripton.setOnClickListener{
                itemView.tv_descripton.isSingleLine = false
                itemView.tv_descripton.ellipsize = null
            }
            itemView.btn_comment.setOnClickListener {
                var intent = Intent(itemView.context,CommentActivity::class.java)
                intent.putExtra("postId",posts.posts.id)
                intent.putExtra("postComment",posts.posts.comments!!.toInt())
                itemView.context.startActivity(intent)
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

    private inner class BlogPlaceImageViewHolder(private val view: View, private val listener: ItemClickListener): RecyclerView.ViewHolder(view),View.OnClickListener
    ,PostPlaceSliderAdapter.ItemClickListener{

        private  val imageSliderAdapter = PostImageSliderAdapter()
        private lateinit var placeSliderAdapter : PostPlaceSliderAdapter
        private lateinit var posts : GetPost

        init {
            view.setOnClickListener(this)
            setUpPlaceRecycler()
        }
        @SuppressLint("SetTextI18n")
        fun bind(postList: List<GetPost>){
            var post = postList[adapterPosition]
            this.posts = post
            Glide.with(itemView) //1
                .load(post.user.profilephoto)
                .placeholder(R.drawable.ic_account_circle)
                .transform(CircleCrop()) //4
                .into(itemView.img_profile)

            Glide.with(itemView)
                .load(post.place!!.gallery[0])
                .into(itemView.image_view_place)

            itemView.tv_username.text = post.user.username
            itemView.tv_time.text = timeInterval(post.posts.time!!, Timestamp.now()) + " ago"
            itemView.tv_descripton.text = post.posts.description
            itemView.tv_total_comment_view.text = "${post.posts.comments} comment"

            updateLikeUI(post)
            uploadUnLikeUI(post)

            Log.e("", post.posts.image_url.toString())
            imageSliderAdapter.setItems(post.posts.image_url)
            itemView.imageSlider.setSliderAdapter(imageSliderAdapter)
            itemView.imageSlider.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION)
            itemView.imageSlider.scrollTimeInSec = 1000

            itemView.tv_place_name_post.text = post.place!!.name
            itemView.tv_place_address_post.text = post.place!!.address

            placeSliderAdapter.setItems(post,postList[adapterPosition].place!!.gallery)

            itemView.img_profile.setOnClickListener{
                val intent = Intent(view.context, Profile::class.java)
                intent.putExtra("user_id",post.user.user_id)
                intent.putExtra("user_name",post.user.username)
                intent.putExtra("user_profile",post.user.profilephoto)
                view.context.startActivity(intent)

            }
            itemView.btn_like.setOnClickListener{
                postHelper.updateLike(post.posts.id!!)
                updateLikeUI(post)
            }
            itemView.btn_icon_unlike_pp.setOnClickListener {
                postHelper.updateUnLike(post.posts.id!!)
                uploadUnLikeUI(post)
            }

            itemView.btn_follow.setOnClickListener{
                println("You Follow ${posts.user.username}")
            }
            itemView.tv_descripton.setOnClickListener{
                itemView.tv_descripton.isSingleLine = false
                itemView.tv_descripton.ellipsize = null
            }
            itemView.btn_comment.setOnClickListener {
                var intent = Intent(itemView.context,CommentActivity::class.java)
                intent.putExtra("postId",posts.posts.id)
                intent.putExtra("postComment",posts.posts.comments!!.toInt())
                itemView.context.startActivity(intent)
            }
        }

        @SuppressLint("SetTextI18n")
        fun updateLikeUI(post : GetPost){
            //get Like Count
            firebaseFirestore.collection("Post/${post.posts.id}/Like").addSnapshotListener { value, error ->
                if (!value!!.isEmpty) {
                    itemView.tv_Like.text = "${value.size()} like"
                } else {
                    itemView.tv_Like.text = "0 like"
                }
            }
            //get Like exit
            firebaseFirestore.collection("Post/${post.posts.id}/Like").document(auth.currentUser!!.uid)
                .addSnapshotListener(EventListener { value, error ->
                    if (value!!.exists()){
                        itemView.btn_like.setBackgroundResource(R.drawable.ic_active_like)
                    }else{
                        itemView.btn_like.setBackgroundResource(R.drawable.ic_like)
                    }
                })
        }

        @SuppressLint("SetTextI18n")
        fun uploadUnLikeUI(post : GetPost){
            //get UnLike Count
            firebaseFirestore.collection("Post/${post.posts.id}/UnLike").addSnapshotListener { value, error ->
                if (!value!!.isEmpty) {
                    itemView.tv_total_unlike.text = "${value.size()} unlike"
                } else {
                    itemView.tv_total_unlike.text = "0 unlike"
                }
            }

            //get UnLike exit
            firebaseFirestore.collection("Post/${post.posts.id}/UnLike").document(auth.currentUser!!.uid)
                .addSnapshotListener(EventListener { value, error ->
                    if (value!!.exists()){
                        itemView.btn_icon_unlike_pp.setBackgroundResource(R.drawable.ic_active_unlike)
                    }else{
                        itemView.btn_icon_unlike_pp.setBackgroundResource(R.drawable.ic_unlike)
                    }
                })
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

        override fun onItemClick(place: Place) {
            val i = Intent(itemView.context, PlaceDetailsActivity::class.java)
            i.putExtra("place_id", place.place_id)
            itemView.context.startActivity(i)
        }

        @SuppressLint("WrongConstant")
        private fun setUpPlaceRecycler() {
            placeSliderAdapter = PostPlaceSliderAdapter(this)
            itemView.place_imageSlider.layoutManager = LinearLayoutManager(itemView.context,LinearLayout.HORIZONTAL,false)
            itemView.place_imageSlider.adapter = placeSliderAdapter
        }
    }

}