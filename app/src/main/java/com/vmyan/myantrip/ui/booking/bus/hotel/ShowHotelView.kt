package com.vmyan.myantrip.ui.booking.bus.hotel

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.SnapHelper
import com.github.rubensousa.gravitysnaphelper.GravitySnapHelper
import com.minbanyar.testbooking.viewModel.hotel.HotelListVMFactory
import com.vmyan.myantrip.R
import com.vmyan.myantrip.data.booking.carRental.hotel.HotelListRepositoryImpl
import com.vmyan.myantrip.model.hotel.HotelList
import com.vmyan.myantrip.ui.adapter.hotel.HotelViewAdapter
import com.vmyan.myantrip.ui.viewmodel.hotel.HotelListViewModel
import com.vmyan.myantrip.utils.Resource
import kotlinx.android.synthetic.main.activity_show_hotel_view.*


class ShowHotelView : AppCompatActivity(), HotelViewAdapter.ItemClickListener ,View.OnClickListener {
    private val viewModel by lazy {
        ViewModelProviders.of(
            this,
            HotelListVMFactory(
                HotelListRepositoryImpl()
            )
        ).get(
            HotelListViewModel::class.java
        )
    }

    private lateinit var cid: String
    private lateinit var hotelViewAdapter : HotelViewAdapter
   lateinit var  hotelAllList :MutableList<HotelList>
    //lateinit var  hotelTopRatingList :MutableList<HotelList>
    private lateinit var locationName :String
    private lateinit var  txtCheckIn :String
    private lateinit var  txtCheckOut:String
    private lateinit var  selectedRoomNo:String
    private lateinit var  selectedGuestsNo :String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_hotel_view)

        cid = intent.getStringExtra("id").toString()
         locationName = intent.getStringExtra("LocationName").toString()
         txtCheckIn = intent.getStringExtra("DateCheckIn").toString()
         txtCheckOut = intent.getStringExtra("DatecheckOut").toString()
         selectedRoomNo =intent.getStringExtra("NumberOfRoom").toString()
         selectedGuestsNo = intent.getStringExtra("NumberOfGuests").toString()
        appComTxtSelectedLocationName.text=locationName
        txtSelectedCheckIn.text=txtCheckIn
        txtSelectedCheckOut.text=txtCheckOut
        appComtxtSelectedRoomNO.text=selectedRoomNo
        appComtxtSelectedGuestsNO.text=selectedGuestsNo

        card_All.setOnClickListener(this)
        card_TopRating.setOnClickListener(this)
        cardHighestPriceHotelList.setOnClickListener(this)
        cardLowestPriceHotelList.setOnClickListener(this)

        setAllHotelList()
        setAdapter()





    }
    @SuppressLint("ShowToast")
    private fun setAllHotelList() {
        //activity
        viewModel.fetchHotelList.observe(this, Observer {
            when (it) {
                is Resource.Loading -> {
                    hotel_list_view_Placeholder.startShimmer()
                    hotel_list_view_Placeholder.visibility = View.VISIBLE
                    rv_hotelList.visibility = View.GONE
                }
                is Resource.Success -> {
                    hotel_list_view_Placeholder.startShimmer()
                    hotel_list_view_Placeholder.visibility = View.GONE
                    rv_hotelList.visibility = View.VISIBLE
                    hotelViewAdapter.setItems(it.data)

                }
                is Resource.Failure -> {
                    println(it.message)
                    hotel_list_view_Placeholder.startShimmer()
                    hotel_list_view_Placeholder.visibility = View.GONE
                    rv_hotelList.visibility = View.GONE
                }
            }
        })
    }

    fun setTopRatingHotelList() {

        viewModel.fetchTopratingHotelList.observe(this, Observer {
            when (it) {
                is Resource.Loading -> {
                    hotel_list_view_Placeholder.startShimmer()
                    hotel_list_view_Placeholder.visibility = View.VISIBLE
                    rv_hotelList.visibility = View.GONE
                }
                is Resource.Success -> {
                    hotel_list_view_Placeholder.startShimmer()
                    hotel_list_view_Placeholder.visibility = View.GONE
                    rv_hotelList.visibility = View.VISIBLE
                    hotelViewAdapter.setItems(it.data)

                }
                is Resource.Failure -> {
                    println(it.message)

                    hotel_list_view_Placeholder.startShimmer()
                    hotel_list_view_Placeholder.visibility = View.GONE
                    rv_hotelList.visibility = View.GONE
                }
            }
        })
    }

    fun setHighestPriceHotelList() {

        viewModel.fetchHighestPriceHotelList.observe(this, Observer {
            when (it) {
                is Resource.Loading -> {
                    hotel_list_view_Placeholder.startShimmer()
                    hotel_list_view_Placeholder.visibility = View.VISIBLE
                    rv_hotelList.visibility = View.GONE
                }
                is Resource.Success -> {
                    hotel_list_view_Placeholder.startShimmer()
                    hotel_list_view_Placeholder.visibility = View.GONE
                    rv_hotelList.visibility = View.VISIBLE
                    hotelViewAdapter.setItems(it.data)

                }
                is Resource.Failure -> {
                    println(it.message)

                    hotel_list_view_Placeholder.startShimmer()
                    hotel_list_view_Placeholder.visibility = View.GONE
                    rv_hotelList.visibility = View.GONE
                }
            }
        })
    }

    fun setLowestPriceHotelList() {

        viewModel.fetchLowestPriceHotelList.observe(this, Observer {
            when (it) {
                is Resource.Loading -> {
                    hotel_list_view_Placeholder.startShimmer()
                    hotel_list_view_Placeholder.visibility = View.VISIBLE
                    rv_hotelList.visibility = View.GONE
                }
                is Resource.Success -> {
                    hotel_list_view_Placeholder.startShimmer()
                    hotel_list_view_Placeholder.visibility = View.GONE
                    rv_hotelList.visibility = View.VISIBLE
                    hotelViewAdapter.setItems(it.data)

                }
                is Resource.Failure -> {
                    println(it.message)

                    hotel_list_view_Placeholder.startShimmer()
                    hotel_list_view_Placeholder.visibility = View.GONE
                    rv_hotelList.visibility = View.GONE
                }
            }
        })
    }


    fun setAdapter() {
        hotelViewAdapter = HotelViewAdapter(this, mutableListOf())
        rv_hotelList.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rv_hotelList.apply {
            setHasFixedSize(true)
            setItemViewCacheSize(20)
            val snapHelperStart: SnapHelper = GravitySnapHelper(Gravity.START)
            snapHelperStart.attachToRecyclerView(rv_hotelList)

            rv_hotelList.adapter = hotelViewAdapter
            ViewCompat.setNestedScrollingEnabled(rv_hotelList, false)
        }
    }


    override fun onItemClick(id: String) {
                Toast.makeText(this,id,Toast.LENGTH_SHORT).show()
                val intent =Intent(this, HotelStepper::class.java)
                intent.putExtra("id",id)
                intent.putExtra("cid",cid)
               // intent.putExtra("Location",locationName)
                intent.putExtra("TxtHotelCheckIn",txtCheckIn)
                intent.putExtra("TxtHotelCheckOut",txtCheckOut)
                intent.putExtra("SelectedRoomNo",selectedRoomNo)
                intent.putExtra("SelectedGuestsNo",selectedGuestsNo)

                startActivity(intent)

    }

    override fun onClick(p0: View?) {
        when(p0){
            card_All -> {
                card_All.setCardBackgroundColor(Color.BLUE)
                card_TopRating.setCardBackgroundColor(Color.TRANSPARENT)
                cardHighestPriceHotelList.setCardBackgroundColor(Color.TRANSPARENT)
                cardLowestPriceHotelList.setCardBackgroundColor(Color.TRANSPARENT)
                setAllHotelList()
            }
            card_TopRating->{
                card_TopRating.setCardBackgroundColor(Color.BLUE)
                card_All.setCardBackgroundColor(Color.TRANSPARENT)
                cardHighestPriceHotelList.setCardBackgroundColor(Color.TRANSPARENT)
                cardLowestPriceHotelList.setCardBackgroundColor(Color.TRANSPARENT)
                setTopRatingHotelList()
            }
            cardHighestPriceHotelList->{
                cardHighestPriceHotelList.setCardBackgroundColor(Color.BLUE)
                cardLowestPriceHotelList.setCardBackgroundColor(Color.TRANSPARENT)
                card_All.setCardBackgroundColor(Color.TRANSPARENT)
                card_TopRating.setCardBackgroundColor(Color.TRANSPARENT)
                setHighestPriceHotelList()
            }
            cardLowestPriceHotelList->{
                cardLowestPriceHotelList.setCardBackgroundColor(Color.BLUE)
                cardHighestPriceHotelList.setCardBackgroundColor(Color.TRANSPARENT)
                card_All.setCardBackgroundColor(Color.TRANSPARENT)
                card_TopRating.setCardBackgroundColor(Color.TRANSPARENT)
                setLowestPriceHotelList()
            }
            else ->{
                card_All.setBackgroundColor(Color.BLUE)

            }
        }
    }
}