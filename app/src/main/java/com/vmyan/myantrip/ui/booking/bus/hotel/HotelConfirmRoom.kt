package com.vmyan.myantrip.ui.booking.bus.hotel

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.vmyan.myantrip.data.booking.carRental.hotel.ConfirmRepositoryImpl
import com.orhanobut.hawk.Hawk
import com.vmyan.myantrip.R
import com.vmyan.myantrip.ui.viewmodel.hotel.ConfirmVM
import com.vmyan.myantrip.ui.viewmodel.hotel.ConfirmVMFactory
import com.vmyan.myantrip.utils.Resource
import kotlinx.android.synthetic.main.fragment_hotel_confirm_room.view.*

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
private const val ARG_PARAM3 = "param3"
private const val ARG_PARAM4 = "param4"
private const val ARG_PARAM5 = "param5"
private const val ARG_PARAM6 = "param6"

class HotelConfirmRoom : Fragment() ,View.OnClickListener{
    private var param1: String? = null
    private var param2: String? = null
    private var param3: String? = null
    private var param4: String? = null
    private var param5: String? = null
    private var param6: String? = null
    private var mListener: OnStepPaymentListener? = null
    private var oneRoomCost :String=""
    lateinit var city : String
    lateinit var guest : String
    lateinit var room :String
    lateinit var hotelName :String
    lateinit var checkIn : String
    lateinit var checkOut : String




    private val viewModel by lazy {
        ViewModelProviders.of(
            this,
            ConfirmVMFactory(
                ConfirmRepositoryImpl()
            )
        ).get(
            ConfirmVM::class.java
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
            param3=  it.getString(ARG_PARAM3)
            param4=  it.getString(ARG_PARAM4)
            param5=  it.getString(ARG_PARAM5)
            param6=  it.getString(ARG_PARAM6)

        }
    }



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       val view =inflater.inflate(R.layout.fragment_hotel_confirm_room, container, false)
        view.txt_contactInfoCheckIn.text=param3
        view.txt_contactInfoCheckOut.text=param4
        checkIn=param3.toString()
        checkOut=param4.toString()
        view.ConformNoOFRoom.text=param5
        view.ConformNoOFGuests.text=param6
        guest= param5.toString()
        room=param6.toString()


       // val discountCost =confirmHotelDiscount.text

      // confirmTotalRoomCost.text=totalCost.toString()
        view.txt_reserveRoom.setOnClickListener (this)
        setConfirm(param1!!,param2!!,view)



        return view
    }

    @SuppressLint("ShowToast")
    fun setConfirm(id: String,cid: String,view : View) {


        viewModel.fectchConfirmDetails(id,cid,Hawk.get<String>("RoomId","7teWbjOgEu14KZmaGKOD")).observe(viewLifecycleOwner, Observer {
            when (it) {
                is Resource.Loading -> {
                    println("loading.....PlaceCategoryList")
                }
                is Resource.Success -> {

                    for (d in it.data){
                       view.confirmFragmentHotelName.text=d.hotelList.hotelName
                        hotelName=d.hotelList.hotelName
                      val address = d.hotelList.hotel_address+","+d.hotelList.hotel_city+","+d.hotelList.hotel_country
                        city=d.hotelList.hotel_city
                       view.confirmHotelAddress.text=address
                        Glide.with(this)
                            .load(d.hotelList.hotel_image)
                            .into(view.confirmHotelImage)
                        view.confirmRoomStatus.text=d.roomList.roomType
                        view.confirmRoomPrice.text=d.roomList.roomPrice
                         oneRoomCost =d.roomList.roomPrice
                        view.confirmTotalRoomCost.text=oneRoomCost

                        view.llConfirmWifi.visibility=View.GONE
                        view.llconfirmAircon.visibility=View.GONE
                        view.llconfirmWithToilet.visibility=View.GONE
                        if (d.roomList.roomWifi){
                            view.llConfirmWifi.visibility=View.VISIBLE
                        }
                        if (d.roomList.roomAirCon){
                            view.llconfirmAircon.visibility=View.VISIBLE
                        }
                        if (d.roomList.roomWithToilet){
                            view.llconfirmWithToilet.visibility=View.VISIBLE
                        }

                    }
                }
                is Resource.Failure -> {
                    println(it.message)
                    Toast.makeText(
                        activity,
                        "An error is ocurred:${it.message}",
                        Toast.LENGTH_SHORT
                    )
                }
            }
        })
    }

    interface OnStepPaymentListener {
        //void onFragmentInteraction(Uri uri);
        fun onNextPressed(fragment: Fragment?)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mListener = if (context is OnStepPaymentListener) {
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
        fun newInstance(param1: String, param2: String,param3 :String,param4 : String,param5 :String,param6 : String) =
            HotelConfirmRoom().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                    putString(ARG_PARAM3,param3)
                    putString(ARG_PARAM4,param4)
                    putString(ARG_PARAM5,param5)
                    putString(ARG_PARAM6,param6)
                }
            }
    }

    override fun onClick(p0: View?) {
        when(p0?.id){
            R.id.txt_reserveRoom->{
                Hawk.put("hCFName",requireView().hotelContactFirstName.text.toString())
                Hawk.put("hCLName",requireView().hotelContactLastName.text.toString())
                Hawk.put("ContactPhoneNo",requireView().hotelContactPhone.text.toString())
               Hawk.put("Guest",guest)
                Hawk.put("Room",room)
                Hawk.put("Location",city)
                Hawk.put("HotelName",hotelName)
                Hawk.put("OneRoomCost",oneRoomCost)
                Hawk.put("CheckIn",checkIn)
                Hawk.put("CheckOut",checkOut)

                if (mListener != null) {
                    mListener!!.onNextPressed(this)

                }

            }
        }
    }

}