package com.vmyan.myantrip.ui.booking.bus.carRental

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.vmyan.myantrip.R

class FinishCarRentails : Fragment() ,View.OnClickListener{


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view =inflater.inflate(R.layout.fragment_finish_car_rentails, container, false)

        return view
    }

    companion object {
        @JvmStatic
        fun newInstance(): FinishCarRentails {
            return FinishCarRentails()
        }
    }

    override fun onClick(p0: View?) {
        TODO("Not yet implemented")
    }
}