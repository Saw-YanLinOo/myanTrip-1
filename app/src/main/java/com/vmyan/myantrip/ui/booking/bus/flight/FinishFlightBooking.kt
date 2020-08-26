package com.vmyan.myantrip.ui.booking.bus.flight

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.orhanobut.hawk.Hawk
import com.vmyan.myantrip.R
import com.vmyan.myantrip.ui.viewmodel.BookingTicketViewModel
import com.vmyan.myantrip.utils.Resource
import kotlinx.android.synthetic.main.fragment_finish_flight_booking.view.*
import kotlinx.android.synthetic.main.fragment_finish_payment.view.*
import org.koin.android.ext.android.inject

class FinishFlightBooking : Fragment() {

    private val viewModel: BookingTicketViewModel by inject()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       val view =inflater.inflate(R.layout.fragment_finish_flight_booking, container, false)
        view.btnFinishFlightBooking.setOnClickListener {
            activity?.finish()
        }

        view.btnFinishFlightBooking.setOnClickListener {
            addTicket(
                Hawk.get("CheckIn"),
                Hawk.get("CheckOut"),
                Hawk.get<String>("OneRoomCost").toLong(),
                Hawk.get("HotelName"),
                "TypeImage"
            )
        }

        return view
    }

    private fun addTicket(
        dateFrom: String,
        dateTo: String,
        totalCost: Long,
        tName: String,
        tTypeImage: String
    ){
        viewModel.addTicket(dateFrom, dateTo, totalCost, tName, tTypeImage).observe(viewLifecycleOwner,{
            when(it){
                is Resource.Loading -> {

                }
                is Resource.Success -> {

                }

                is Resource.Failure -> {

                }
            }
        })
    }

    companion object {
        @JvmStatic
        fun newInstance(): FinishFlightBooking {
            return FinishFlightBooking()
        }
    }


}