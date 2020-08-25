package com.vmyan.myantrip.ui.booking.bus

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.vmyan.myantrip.R



class BusFinishPayment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view =inflater.inflate(R.layout.fragment_bus_finish_payment, container, false)

        return view
    }

    companion object {
        @JvmStatic
        fun newInstance(): BusFinishPayment {
            return BusFinishPayment()
        }
    }
}