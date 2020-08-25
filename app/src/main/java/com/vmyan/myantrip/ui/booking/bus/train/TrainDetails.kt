package com.vmyan.myantrip.ui.booking.bus.train

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.vmyan.myantrip.R
import kotlinx.android.synthetic.main.fragment_train_details.view.*


class TrainDetails : Fragment(),View.OnClickListener {
    private var mListener: TrainDetails.OnTrianDetails? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view =inflater.inflate(R.layout.fragment_train_details, container, false)
        view.cardSelectedTrainContinue.setOnClickListener(this)
        return view
    }
    interface OnTrianDetails {
        //void onFragmentInteraction(Uri uri);
        fun onNextPressed(fragment: Fragment?)
    }
    override fun onAttach(context: Context) {
        super.onAttach(context)
        mListener = if (context is OnTrianDetails) {
            context
        } else {
            throw RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener")
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(): TrainDetails {
            return TrainDetails()
        }
    }

    override fun onClick(p0: View?) {
        when(p0?.id){
            R.id.cardSelectedTrainContinue ->{
                if (mListener != null) {
                    mListener!!.onNextPressed(this)
                }
            }
        }
    }
}