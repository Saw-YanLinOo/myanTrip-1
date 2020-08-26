package com.vmyan.myantrip.ui.booking.bus.hotel

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.Timestamp
import com.orhanobut.hawk.Hawk
import com.vmyan.myantrip.R
import com.vmyan.myantrip.ui.viewmodel.BookingTicketViewModel
import com.vmyan.myantrip.utils.Resource

import kotlinx.android.synthetic.main.fragment_finish_payment.view.*
import org.koin.android.ext.android.inject

class FinishPayment : Fragment() {

    private val viewModel: BookingTicketViewModel by inject()

        lateinit var fName : String
        lateinit var lName : String
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =inflater.inflate(R.layout.fragment_finish_payment, container, false)
         fName = Hawk.get("hCFName")
        lName =Hawk.get("hCLName")
        view.btnFinishHotelBooking.setOnClickListener {
          requireActivity().finish()
        }
        view.txtContactName.text= fName+lName
        view.ConformSelectedNoOFGuests.text = Hawk.get("Guest")
        view.ConformSelectedNoOFRoom.text =Hawk.get("Room")
        view.txtSelectedLocation.text=Hawk.get("Location")
        view.txtSelectedItemName.text =Hawk.get("HotelName")
        view.txtContactPhoneNo.text=Hawk.get("ContactPhoneNo")
        view.txtSelectedItemCheckIn.text=Hawk.get("CheckIn")
        view.txtSelectedItemCheckOut.text=Hawk.get("CheckOut")
        view.txtSelectedItemTotalCost.text=Hawk.get("OneRoomCost")

        view.btnFinishHotelBooking.setOnClickListener {
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
        fun newInstance(): FinishPayment {
            return FinishPayment()
        }
    }

}