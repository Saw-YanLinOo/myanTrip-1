package com.vmyan.myantrip.ui.booking.bus.train

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.firebase.Timestamp
import com.vmyan.myantrip.R
import com.vmyan.myantrip.ui.booking.bus.PostActivityContract
import com.vmyan.myantrip.ui.booking.bus.hotel.HotelBooking
import com.vmyan.myantrip.ui.bs.SelectTrainPassenger
import kotlinx.android.synthetic.main.activity_car_rental.*
import kotlinx.android.synthetic.main.activity_train_booking.*
import java.util.*
import kotlin.time.ExperimentalTime

class TrainBooking : AppCompatActivity() {
    private var dTrainDepartDate : String=""
    private var trainFrom : String=""
    private var trainFromImage: String=""
    private var trainTo : String=""
    private var trainToImage : String=""
    private var startDate: Timestamp? = null
    @ExperimentalTime
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_train_booking)

        pickPassenger()
        rl_trainDepartDate.setOnClickListener {
            datePicker("start")
        }
        pickPlace()

        card_TrainSearch.setOnClickListener {
            val intent =Intent(this, TrainViewList::class.java)
            startActivity(intent)
        }
    }
    @SuppressLint("ResourceType")
    private fun pickPassenger(){
        rl_TrainPickPassenger.setOnClickListener {
            val bottomSheet= SelectTrainPassenger()
            bottomSheet.show(supportFragmentManager,bottomSheet.tag)
        }
    }
    private fun pickPlace() {
        rl_TrainFrom.setOnClickListener {
            openPostActivityCustom.launch(1)
            //lightFrom=txt_FligtFrom.text.toString()

        }
        rl_TrainTo.setOnClickListener {
            trainToClicked.launch(1)
        }
    }
    private val openPostActivityCustom =
        registerForActivityResult(PostActivityContract()) { result ->
            if (result != null) {
                txtTrainFrom.text = result[0]
              //  Glide.with(this).load(result[1]).into(imgFlightFrom)
                trainFrom = result[0]
                trainFromImage=result[1]
            }
        }
    private val trainToClicked =
        registerForActivityResult(PostActivityContract()) { result ->
            if (result != null) {
                txtTrainTo.text = result[0]
               // Glide.with(this).load(result[1]).into(imgFlightTo)
                trainTo = result[0]
                trainToImage=result[1]
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
                    dTrainDepartDate=picker.headerText
                    startDate = Timestamp(Date(it))
                }
            }
        }
    }
}