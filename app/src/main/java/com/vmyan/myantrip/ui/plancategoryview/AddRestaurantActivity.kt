package com.vmyan.myantrip.ui.plancategoryview

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.RequiresApi
import com.bumptech.glide.Glide
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.firebase.Timestamp
import com.vmyan.myantrip.R
import com.vmyan.myantrip.ui.TripPlanActivity
import com.vmyan.myantrip.ui.viewmodel.AddPlanViewModel
import com.vmyan.myantrip.utils.DateRange
import com.vmyan.myantrip.utils.Resource
import kotlinx.android.synthetic.main.activity_add_restaurant.*
import org.koin.android.ext.android.inject
import java.text.SimpleDateFormat
import java.util.*

class AddRestaurantActivity : AppCompatActivity() {


    private val viewModel: AddPlanViewModel by inject()

    private lateinit var tripStartDate: Timestamp
    private lateinit var tripEndDate: Timestamp
    private lateinit var tripId: String
    private var checkInDate: Timestamp? = null
    private var checkInTime: String? = null

    @SuppressLint("SetTextI18n")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_restaurant)


        tripId = intent.getStringExtra("tripId")!!
        tripStartDate = intent.getParcelableExtra("tripStartDate")!!
        tripEndDate = intent.getParcelableExtra("tripEndDate")!!
        val planName = intent.getStringExtra("planName")
        val planImg = intent.getStringExtra("planImg")
        titleText.text = "Add $planName"
        Glide.with(this).load(planImg).into(titleImg)

        plan_addrestaurant_date.setOnClickListener {
            datePicker()
        }


        plan_addrestaurant_time.setOnClickListener {
            pickTime()
        }

        var confirm = true

        if (yes.isChecked){
            confirm = true
        }else if (no.isChecked){
            confirm = false
        }

        plan_addrestaurant_addbtn.setOnClickListener {
            addPlan(
                tripId,
                plan_addrestaurant_name.text.toString(),
                planImg!!,
                checkInDate!!,
                null,
                checkInTime!!,
                "",
                plan_addrestaurant_state.text.toString(),
                "",
                plan_addrestaurant_city.text.toString(),
                "",
                plan_addrestaurant_address.text.toString(),
                "",
                plan_addrestaurant_cost.text.toString().toInt(),
                confirm,
                "",
                "",
                "",
                planName!!
            )
        }

    }


    private fun addPlan(
        tripId: String,
        name: String,
        img: String,
        fromDate: Timestamp,
        toDate: Timestamp?,
        fromTime: String,
        toTime: String,
        fromState: String,
        toState: String,
        fromCity: String,
        toCity: String,
        fromAddress: String,
        toAddress: String,
        estimationCost: Int,
        confirmation: Boolean,
        type: String,
        description: String,
        details: String,
        viewType: String
    ){
        viewModel.addPlan(
            tripId, name, img, fromDate,
            toDate, fromTime, toTime, fromState, toState, fromCity, toCity, fromAddress, toAddress, estimationCost, confirmation, type, description, details, viewType
        ).observe(this,{
            when(it){
                is Resource.Loading ->{

                }
                is Resource.Success ->{
                    val intent = Intent(this, TripPlanActivity::class.java)
                    intent.putExtra("tripId",tripId)
                    startActivity(intent)
                }
                is Resource.Failure ->{

                }
            }
        })
    }



    @SuppressLint("SimpleDateFormat")
    @RequiresApi(Build.VERSION_CODES.O)
    private fun datePicker(){
        val builder = MaterialDatePicker.Builder.datePicker()
        builder.setCalendarConstraints(
            DateRange.limitRange(
                SimpleDateFormat("dd-MM-yyyy").format(tripStartDate.toDate()),
                SimpleDateFormat("dd-MM-yyyy").format(tripEndDate.toDate()),
                "other"
            )!!.build())
        val picker = builder.build()
        picker.show(supportFragmentManager, picker.toString())
        picker.addOnPositiveButtonClickListener {
            plan_addrestaurant_date.text = picker.headerText
            checkInDate = Timestamp(Date(it))
        }
    }

    private fun pickTime(){
        val picker = MaterialTimePicker()
        picker.show(supportFragmentManager,"timepicker")
        picker.setListener {
            val result = "${it.hour}:${it.minute}"
            plan_addrestaurant_time.text = DateRange.setAMPM(it.hour,it.minute)
            checkInTime = result
        }
    }
}