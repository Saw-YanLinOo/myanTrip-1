package com.vmyan.myantrip.ui.booking.bus.hotel

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.SnapHelper
import com.github.rubensousa.gravitysnaphelper.GravitySnapHelper
import com.vmyan.myantrip.ui.adapter.hotel.RoomImageSliderAdapter
import com.vmyan.myantrip.ui.adapter.hotel.RoomLIstViewAdapter
import com.vmyan.myantrip.model.hotel.RoomImgSliderItem
import com.orhanobut.hawk.Hawk
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType
import com.smarteist.autoimageslider.SliderAnimations
import com.smarteist.autoimageslider.SliderView
import com.vmyan.myantrip.R
import com.vmyan.myantrip.data.booking.carRental.hotel.RoomRepositoryImpl
import com.vmyan.myantrip.ui.viewmodel.hotel.RoomListVM
import com.vmyan.myantrip.ui.viewmodel.hotel.RoomListVMFactory
import com.vmyan.myantrip.utils.Resource
import kotlinx.android.synthetic.main.fragment_hotel_room_list.*
import kotlinx.android.synthetic.main.fragment_hotel_room_list.view.*


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class HotelRoomList : Fragment(), RoomLIstViewAdapter.ItemClickListener {
    private var param1: String? = null
    private var param2: String? = null
    private var checkIn : String?=null
    private var checkOut : String?=null

    private var mListener: OnStepOneListener? = null

    private val viewModel by lazy {
        ViewModelProviders.of(
            this,
            RoomListVMFactory(
                RoomRepositoryImpl()
            )
        ).get(
            RoomListVM::class.java
        )
    }

    lateinit var imageRoomList: List<RoomImgSliderItem>
    private lateinit var roomListViewAdapter: RoomLIstViewAdapter
    private lateinit var roomImageSliderAdapter: RoomImageSliderAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
            checkIn=it.getString("CheckIn")
            checkOut=it.getString("CheckOut")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =inflater.inflate(R.layout.fragment_hotel_room_list,container,false)
        view.hotelSelectedCheckIn.text=checkIn
        view.hotelSelectedCheckOut.text=checkOut

        showImageSlider(view)
        showRoomList(view)

        setAllRoomList(param1!!,param2!!,view)



        return view
    }
    fun showRoomList(view : View) {
        roomListViewAdapter = RoomLIstViewAdapter(this,mutableListOf())
       view.rv_roomList.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        view.rv_roomList.apply {
            setHasFixedSize(true)
            setItemViewCacheSize(20)
            val snapHelperStart: SnapHelper = GravitySnapHelper(Gravity.START)
            snapHelperStart.attachToRecyclerView(rv_roomList)

            rv_roomList.adapter = roomListViewAdapter
            ViewCompat.setNestedScrollingEnabled(rv_roomList, false)
        }
    }
    private fun showImageSlider(view : View) {
        roomImageSliderAdapter = RoomImageSliderAdapter()
       view.sv_RoomImage.setSliderAdapter(roomImageSliderAdapter)        //set indicator animation by using SliderLayout.IndicatorAnimations. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!
        view.sv_RoomImage.setIndicatorAnimation(IndicatorAnimationType.WORM)
        view. sv_RoomImage.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION)
        view.sv_RoomImage.autoCycleDirection = SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH
        view. sv_RoomImage.indicatorSelectedColor = Color.WHITE
        view.sv_RoomImage.indicatorUnselectedColor = Color.GRAY
        view.sv_RoomImage.scrollTimeInSec = 3
        view.sv_RoomImage.isAutoCycle = true
        view.sv_RoomImage.startAutoCycle()

    }
    @SuppressLint("ShowToast")
    fun setAllRoomList(id: String,cid: String,view : View) {
        println(id)
        println(cid)

        viewModel.fetchAllRoomList(id,cid).observe(viewLifecycleOwner, Observer {
            when (it) {
                is Resource.Loading -> {
                    rv_roomListPlaceHolder.startShimmer()
                    rv_roomListPlaceHolder.visibility = View.VISIBLE
                    rv_roomList.visibility = View.GONE
                }
                is Resource.Success -> {

                    for (d in it.data){

                        val address =d.hotelList.hotel_address+","+ d.hotelList.hotel_city+","+d.hotelList.hotel_country
                        view.selected_hotelName.text = d.hotelList.hotelName
                        view.selec_hotelAddress.text=address
                        view.selectedHotelPhoneNo.text=d.hotelList.hotel_PhoneNo

                        rv_roomListPlaceHolder.startShimmer()
                        rv_roomListPlaceHolder.visibility = View.GONE
                        rv_roomList.visibility = View.VISIBLE

                        roomImageSliderAdapter.setItem(d.hotelList.roomImages)


                        roomListViewAdapter.setItems(d.roomList)
                    }
                }
                is Resource.Failure -> {
                    rv_roomListPlaceHolder.startShimmer()
                    rv_roomListPlaceHolder.visibility = View.GONE
                    rv_roomList.visibility = View.GONE
                }
            }
        })
    }

    interface OnStepOneListener {
        //void onFragmentInteraction(Uri uri);
        fun onNextPressed(fragment: Fragment?)
    }
    override fun onAttach(context: Context) {
        super.onAttach(context)
        mListener = if (context is OnStepOneListener) {
            context
        } else {
            throw RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener")
        }
    }


    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String,hotelCheckIn : String,hotelCheckOut : String) =
            HotelRoomList().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                    putString("CheckIn",hotelCheckIn)
                    putString("CheckOut",hotelCheckOut)
                }
            }
    }

    override fun onItemClick(roomId: String) {
            Hawk.put("RoomId",roomId)

        if (mListener != null) {
            mListener!!.onNextPressed(this)
        }
    }
}