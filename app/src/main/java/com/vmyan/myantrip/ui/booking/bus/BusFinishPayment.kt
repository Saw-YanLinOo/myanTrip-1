package com.vmyan.myantrip.ui.booking.bus

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.orhanobut.hawk.Hawk
import com.vmyan.myantrip.R
import com.vmyan.myantrip.ui.viewmodel.BookingTicketViewModel
import com.vmyan.myantrip.utils.Resource
import kotlinx.android.synthetic.main.fragment_bus_finish_payment.view.*
import kotlinx.android.synthetic.main.fragment_train_finish.view.*
import org.koin.android.ext.android.inject


class BusFinishPayment : Fragment() ,View.OnClickListener{

    private val viewModel: BookingTicketViewModel by inject()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view =inflater.inflate(R.layout.fragment_bus_finish_payment, container, false)
        view.btnFinishBusBooking.setOnClickListener {
            requireActivity().finish()
            addTicket(
                "27/8/2020",
                "",
               16200,
                "Shwe Sin Set Kyar",
                "https://firebasestorage.googleapis.com/v0/b/myantrip-45671.appspot.com/o/ticketType%2Fbus.png?alt=media&token=17c521bc-e84c-434c-96de-578f31be2467"
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
        fun newInstance(): BusFinishPayment {
            return BusFinishPayment()
        }
    }

    override fun onClick(p0: View?) {

    }
}