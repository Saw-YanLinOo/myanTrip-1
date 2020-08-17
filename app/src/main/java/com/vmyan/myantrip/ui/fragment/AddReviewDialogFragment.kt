package com.vmyan.myantrip.ui.fragment

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.DialogFragment
import com.bumptech.glide.Glide
import com.google.firebase.Timestamp
import com.orhanobut.hawk.Hawk
import com.vmyan.myantrip.R
import com.vmyan.myantrip.model.PlaceDetails
import com.vmyan.myantrip.ui.viewmodel.PlaceDetailsViewModel
import com.vmyan.myantrip.utils.Resource
import kotlinx.android.synthetic.main.addreview_dialogfragment.*
import kotlinx.android.synthetic.main.addreview_dialogfragment.view.*


class AddReviewDialogFragment(
    private val place: PlaceDetails,
    private val rating: Float,
    private val viewModel: PlaceDetailsViewModel
) : DialogFragment() {


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.addreview_dialogfragment, container, false)

        view.ra_backbtn.setOnClickListener {
            dismiss()
        }
        view.ra_backbtn_ly.setOnClickListener {
            dismiss()
        }

        view.ra_name.text = place.place.name
        Glide.with(view)
            .load(place.place.mainImg)
            .into(view.ra_mainImg)
        view.add_rval.rating = rating
        view.cancel_btn.setOnClickListener {
            dismiss()
        }
        view.cancel_btn_ly.setOnClickListener {
            dismiss()
        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.post_btn_ly.setOnClickListener {
            postReview(desc_input.text.toString(), add_rval.rating)

            val dialogListener = activity as DialogListener
            dialogListener.onFinishDialog(place.place.place_id)


            val imm =
                requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0)
            dismiss()
        }
        view.post_btn.setOnClickListener {
            postReview(desc_input.text.toString(), add_rval.rating)

            val dialogListener = activity as DialogListener
            dialogListener.onFinishDialog(place.place.place_id)

            val imm =
                requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0)
            dismiss()
        }
    }

    private fun postReview(desc: String, rating: Float){
        val username = Hawk.get<String>("user_name")
        val userId = Hawk.get<String>("user_id")
        val userImg = Hawk.get<String>("user_profile")
        viewModel.addReview(place.cat_id,place.subcat_id,place.place.place_id, userId,username, userImg,rating,desc, Timestamp.now())
            .observe(requireActivity(), androidx.lifecycle.Observer {
                when(it) {
                    is Resource.Loading -> {

                    }
                    is Resource.Success -> {
                        println(it.data)
                    }
                    is Resource.Failure -> {
                        println(it.message)
                    }
                }
            })
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        return dialog
    }

    interface DialogListener {
        fun onFinishDialog(id: String)
    }



}