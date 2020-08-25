package com.vmyan.myantrip.ui.booking.bus.carRental

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.firebase.Timestamp
import com.vmyan.myantrip.R
import com.vmyan.myantrip.ui.booking.bus.PostActivityContract
import com.vmyan.myantrip.ui.booking.bus.hotel.HotelBooking
import kotlinx.android.synthetic.main.activity_car_rental.*
import java.util.*
import kotlin.time.ExperimentalTime

class CarRental : AppCompatActivity() {
    private var dPickUpCar : String=""
    private var dDropOffCar : String=""
    private var location : String=""
    private var cityImagge : String=""
    private var startDate: Timestamp? = null
    private var endDate: Timestamp? = null
    @ExperimentalTime
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_car_rental)
        pickPlace()
        card_CarRentailSearch.setOnClickListener {
            val intent= Intent (this, CarRentalsListView::class.java)
            startActivity(intent)

        }
        rl_PickUp_CarRental.setOnClickListener {
            datePicker("start")
        }
        rl_DropOff_CarRentals.setOnClickListener {
            datePicker("end")
        }
    }

    private fun pickPlace() {
        rl_carRentalPickLocation.setOnClickListener {
            openPostActivityCustom.launch(1)
            location=txtCarRentalLocation.text.toString()

        }
    }
    private val openPostActivityCustom =
        registerForActivityResult(PostActivityContract()) { result ->
            if (result != null) {
                txtCarRentalLocation.text = result[0]
                location = result[0]
                cityImagge=result[1]
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
                    txtPickUpCarRentals.text = picker.headerText
                    dPickUpCar=picker.headerText
                    startDate = Timestamp(Date(it))
                }
                "end" -> {
                    txt_DropOffCarRentals.text = picker.headerText
                    dDropOffCar=picker.headerText
                    endDate = Timestamp(Date(it))
                }
            }
        }
    }
}