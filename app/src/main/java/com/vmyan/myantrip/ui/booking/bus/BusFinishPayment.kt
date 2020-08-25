package com.vmyan.myantrip.ui.booking.bus

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.vmyan.myantrip.R
import kotlinx.android.synthetic.main.fragment_bus_finish_payment.view.*


class BusFinishPayment : Fragment() ,View.OnClickListener{

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view =inflater.inflate(R.layout.fragment_bus_finish_payment, container, false)
            view.btnFinishBusBooking.setOnClickListener {
                requireActivity().finish()
            }
        return view
    }

    companion object {
        @JvmStatic
        fun newInstance(): BusFinishPayment {
            return BusFinishPayment()
        }
    }

    override fun onClick(p0: View?) {

    }
}