package com.vmyan.myantrip.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.smarteist.autoimageslider.SliderViewAdapter
import com.vmyan.myantrip.R
import com.vmyan.myantrip.model.GetPost
import com.vmyan.myantrip.model.Place
import kotlinx.android.synthetic.main.image_slider_layout_item.view.*
import kotlinx.android.synthetic.main.image_slider_layout_item.view.iv_auto_image_slider
import kotlinx.android.synthetic.main.place_slider_layout_item.view.*


class PostPlaceSliderAdapter(private val listener: PostPlaceSliderAdapter.ItemClickListener): RecyclerView.Adapter<PostPlaceSliderAdapter.PlaceAdapterVH>() {

    private var imageList = ArrayList<String>()
    private lateinit var postList:MutableLiveData<GetPost>

    interface ItemClickListener {
        fun onItemClick(place : Place)
    }

    fun setItems(postList: GetPost, imageList : ArrayList<String>){
        this.imageList.clear()
        this.imageList.addAll(imageList)
        this.postList = MutableLiveData(postList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaceAdapterVH {
        return PlaceAdapterVH(listener,LayoutInflater.from(parent.context).inflate(R.layout.place_slider_layout_item,parent,false))
    }

    override fun onBindViewHolder(holder: PlaceAdapterVH, position: Int) {
        holder.bind(postList.value!!,imageList[position])
    }

    override fun getItemCount(): Int {
        return imageList.size
    }

    class PlaceAdapterVH(var listener: ItemClickListener,view: View) : RecyclerView.ViewHolder(view),View.OnClickListener {
        private lateinit var post: GetPost

        init {

            itemView.setOnClickListener(this)
        }

        fun bind(post : GetPost,image : String){
            this.post = post
            val circularProgressDrawable = CircularProgressDrawable(itemView.context)
            circularProgressDrawable.strokeWidth = 5f
            circularProgressDrawable.centerRadius = 30f
            circularProgressDrawable.start()

            val requestOptions = RequestOptions()
            requestOptions.placeholder(circularProgressDrawable)
            requestOptions.error(R.drawable.ic_error)
            requestOptions.skipMemoryCache(true)
            requestOptions.fitCenter()

            itemView.tv_place_name_post.text = post.place!!.name
            itemView.tv_place_address_post.text = post.place!!.address

            Glide.with(itemView)
                .load(image)
                .apply(requestOptions)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(itemView.iv_auto_place_slider)
        }

        override fun onClick(p0: View?) {
            listener.onItemClick(this.post.place!!)
        }
    }
}