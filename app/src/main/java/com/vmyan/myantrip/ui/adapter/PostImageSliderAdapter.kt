package com.vmyan.myantrip.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.smarteist.autoimageslider.SliderViewAdapter
import com.vmyan.myantrip.R
import kotlinx.android.synthetic.main.image_slider_layout_item.view.*


class PostImageSliderAdapter: SliderViewAdapter<PostImageSliderAdapter.SliderAdapterVH>() {
    private val imageList:ArrayList<String> = arrayListOf()

    fun setItems(imageList: ArrayList<String>){
        this.imageList.clear()
        this.imageList.addAll(imageList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup): SliderAdapterVH {
        val view =LayoutInflater.from(parent.context).inflate(R.layout.image_slider_layout_item, null);
        return SliderAdapterVH(view)
    }

    override fun getCount(): Int {
        return imageList.size
    }

    override fun onBindViewHolder(viewHolder: SliderAdapterVH, position: Int) {
        (viewHolder as SliderAdapterVH).bind(imageList[position])
    }

    class SliderAdapterVH(itemView: View) :SliderViewAdapter.ViewHolder(itemView) {
        fun bind(imgList: String){

            val circularProgressDrawable = CircularProgressDrawable(itemView.context)
            circularProgressDrawable.strokeWidth = 5f
            circularProgressDrawable.centerRadius = 30f
            circularProgressDrawable.start()

            val requestOptions = RequestOptions()
            requestOptions.placeholder(circularProgressDrawable)
            requestOptions.error(R.drawable.ic_error)
            requestOptions.skipMemoryCache(true)
            requestOptions.fitCenter()

            Glide.with(itemView)
                .load(imgList)
                .apply(requestOptions)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(itemView.iv_auto_image_slider)
        }
    }

}