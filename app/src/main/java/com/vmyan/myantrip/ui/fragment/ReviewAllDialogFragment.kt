package com.vmyan.myantrip.ui.fragment

import android.app.Dialog
import android.os.Bundle
import android.view.*
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.SnapHelper
import com.bumptech.glide.Glide
import com.github.rubensousa.gravitysnaphelper.GravitySnapHelper
import com.vmyan.myantrip.R
import com.vmyan.myantrip.model.Place
import com.vmyan.myantrip.model.Review
import com.vmyan.myantrip.ui.adapter.ReviewListAdapter
import kotlinx.android.synthetic.main.reviewall_dialog.*
import kotlinx.android.synthetic.main.reviewall_dialog.view.*


class ReviewAllDialogFragment(private val list: MutableList<Review>, private val place: Place) : DialogFragment() {

    private lateinit var reviewListAdapter: ReviewListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.reviewall_dialog, container, false)

        view.ra_backbtn.setOnClickListener {
            dismiss()
        }
        view.ra_backbtn_ly.setOnClickListener {
            dismiss()
        }

        view.ra_name.text = place.name
        Glide.with(view)
            .load(place.mainImg)
            .into(view.ra_mainImg)

        setUpReviewListRecycler(view)
        reviewListAdapter.setItems(list)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        return dialog
    }

    private fun setUpReviewListRecycler(view: View){
        reviewListAdapter = ReviewListAdapter(mutableListOf())
        view.review_recycler.layoutManager = LinearLayoutManager(activity,
            LinearLayoutManager.VERTICAL, false)
        view.review_recycler.apply {
            setHasFixedSize(true)
            setItemViewCacheSize(20)
        }

        val snapHelperStart: SnapHelper = GravitySnapHelper(Gravity.TOP)
        snapHelperStart.attachToRecyclerView(view.review_recycler)
        view.review_recycler.isNestedScrollingEnabled = false
        view.review_recycler.adapter = reviewListAdapter

    }


}