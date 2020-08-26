package com.vmyan.myantrip.ui.booking.bus.carRental

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.vmyan.myantrip.R
import com.vmyan.myantrip.ui.booking.bus.hotel.HotelConfirmRoom
import kotlinx.android.synthetic.main.fragment_car_rentails_details.view.*

class CarRentalsDetails : Fragment(),View.OnClickListener {

    private var mListener: OnCarDetailsListener? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       val view =inflater.inflate(R.layout.fragment_car_rentails_details, container, false)
        view.llCarRentalNext.setOnClickListener(this)

        return view
    }
    interface OnCarDetailsListener {
        //void onFragmentInteraction(Uri uri);
        fun onNextPressed(fragment: Fragment?)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mListener = if (context is OnCarDetailsListener) {
            context
        } else {
            throw RuntimeException(
                context.toString()
                        + " must implement OnFragmentInteractionListener"
            )
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(): CarRentalsDetails {
            return CarRentalsDetails()
        }
    }

    override fun onClick(p0: View?) {
        if (p0 != null) {
            when(p0.id){
                R.id.llCarRentalNext->{

                    if (mListener != null) {
                        mListener!!.onNextPressed(this)

                    }
                }
            }
        }
    }
}