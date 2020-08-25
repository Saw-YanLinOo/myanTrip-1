package com.vmyan.myantrip.ui.booking.bus.flight

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.vmyan.myantrip.R
import kotlinx.android.synthetic.main.fragment_finish_flight_booking.view.*

class FinishFlightBooking : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       val view =inflater.inflate(R.layout.fragment_finish_flight_booking, container, false)
        view.btnFinishFlightBooking.setOnClickListener {
            activity?.finish()
        }
        return view
    }

    companion object {
        @JvmStatic
        fun newInstance(): FinishFlightBooking {
            return FinishFlightBooking()
        }
    }


}