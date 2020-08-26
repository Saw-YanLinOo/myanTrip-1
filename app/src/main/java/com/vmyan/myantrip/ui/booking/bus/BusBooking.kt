package com.vmyan.myantrip.ui.booking.bus

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.Toast
import androidx.core.view.ViewCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SnapHelper
import com.bumptech.glide.Glide
import com.github.rubensousa.gravitysnaphelper.GravitySnapHelper
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.firebase.Timestamp
import com.vmyan.myantrip.R
import com.vmyan.myantrip.model.bus.BusRecentItem
import com.vmyan.myantrip.model.hotel.HotelRecentItem
import com.vmyan.myantrip.ui.adapter.bus.BusRecentAdapter
import com.vmyan.myantrip.ui.adapter.hotel.HotelRecentAdapter
import com.vmyan.myantrip.ui.booking.bus.hotel.HotelBooking
import com.vmyan.myantrip.ui.viewmodel.bus.BusRecentViewModel
import com.vmyan.myantrip.ui.viewmodel.hotel.RecentViewModel
import kotlinx.android.synthetic.main.activity_bus_booking.*
import kotlinx.android.synthetic.main.activity_hotel_booking.*
import java.util.*
import kotlin.time.ExperimentalTime

class BusBooking : AppCompatActivity(), View.OnClickListener {
    private var locationFrom : String=""
    private var cityImageFrom : String=""
    private var locationTo : String=""
    private var cityImageTo : String=""
    private var busDepartDate : String=""
    private var seatNumber : String=""
    private lateinit var recentViewModel: BusRecentViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bus_booking)
            pickPlace()
            setUpAdapter()
        imgBackArrow.setOnClickListener(this)
        rl_BusDepartDate.setOnClickListener(this)
        clearAllBusRecentValue.setOnClickListener(this)
        card_BusSearch.setOnClickListener {
            val intent = Intent (this, BusListView::class.java)
            intent.putExtra("BusForm",locationFrom)
            intent.putExtra("BusImageFrom",cityImageFrom)
            intent.putExtra("BusTo",locationTo)
            intent.putExtra("BusCityImageTo",cityImageTo)
            intent.putExtra("SeatTotalNo",seatNumber)
            intent.putExtra("BusDepartsDate",busDepartDate)
            startActivity(intent)
            recentViewModel.insert(BusRecentItem(locationFrom,locationTo,busDepartDate))
        }

        busSeat_number_picker.setOnValueChangedListener { picker, oldVal, newVal ->
            seatNumber =newVal.toString()

        }
    }
    private fun setUpAdapter() {
        val recyclerView = findViewById<RecyclerView>(R.id.rv_BusRecentItem)
        val adapter = BusRecentAdapter(this)
        ViewCompat.setNestedScrollingEnabled(rv_BusRecentItem, false)
        recyclerView.adapter = adapter
        recyclerView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        recentViewModel = ViewModelProvider(this).get(BusRecentViewModel::class.java)
        recentViewModel.allItems.observe(this,  { words ->
            // Upd
            // ate the cached copy of the words in the adapter.
            words?.let { adapter.setWords(it) }
        })
    }

    private fun pickPlace() {
        llBusFrom.setOnClickListener {
            cityFromOpenPostActivityCustom.launch(1)
           // location=appComTxtPickLoacation.text.toString()

        }
        llBusTo.setOnClickListener {
            cityToOpenPostActivityCustom.launch(1)
            //location=appComTxtPickLoacation.text.toString()
        }
    }
    private val cityFromOpenPostActivityCustom =
        registerForActivityResult(CityFromPostActivityContract()) { result ->
            if (result != null) {
                txt_BusFrom.text = result[0]
                Glide.with(this).load(result[1]).into(imgBusFrom)
                locationFrom = result[0]
               cityImageFrom=result[1]
            }
        }
    private val cityToOpenPostActivityCustom =
        registerForActivityResult(CityToPostActivityContract()) { result ->
            if (result != null) {
                txt_BusTo.text = result[0]
                Glide.with(this).load(result[1]).into(imgBusTo)
                locationTo = result[0]
                cityImageTo=result[1]
            }
        }
    private fun limitRange(): CalendarConstraints.Builder? {
        val constraintsBuilderRange = CalendarConstraints.Builder()
        val calendarStart = Calendar.getInstance()
        val calendarEnd = Calendar.getInstance()
        val year = 2022
//        val startMonth = 2
//        val startDate = 15
        val endMonth = 12
        val endDate = 31
        //calendarStart[year, startMonth - 1] = startDate - 1
        calendarEnd[year, endMonth - 1] = endDate
        val minDate = calendarStart.timeInMillis
        val maxDate = calendarEnd.timeInMillis
        constraintsBuilderRange.setStart(minDate)
        constraintsBuilderRange.setEnd(maxDate)
        constraintsBuilderRange.setValidator(HotelBooking.RangeValidator(minDate, maxDate))
        return constraintsBuilderRange
    }
    @ExperimentalTime
    private fun datePicker(status: String){
        val builder = MaterialDatePicker.Builder.datePicker()
        builder.setCalendarConstraints(limitRange()!!.build())
        val picker = builder.build()
        picker.show(supportFragmentManager, picker.toString())
        picker.addOnPositiveButtonClickListener {
            when(status){
                "start" -> {
                    busDepartDate=picker.headerText
                    appBusDepartDate.text=picker.headerText
                   // startDate = Timestamp(Date(it))
                }
            }
        }
    }

    @ExperimentalTime
    override fun onClick(p0: View?) {
        when(p0?.id){
            R.id.rl_BusDepartDate->{
                datePicker("start")
            }
            R.id.clearAllBusRecentValue->{
                recentViewModel.clearAll()
            }
            R.id.imgBackArrow->{
                this.finish()
            }
        }

    }
}