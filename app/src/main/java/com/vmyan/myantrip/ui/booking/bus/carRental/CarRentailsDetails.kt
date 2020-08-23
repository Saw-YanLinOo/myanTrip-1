package com.vmyan.myantrip.ui.booking.bus.carRental

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.vmyan.myantrip.R

class CarRentailsDetails : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_car_rentails_details, container, false)
    }

    companion object {
        @JvmStatic
        fun newInstance(): CarRentailsDetails {
            return CarRentailsDetails()
        }
    }
}