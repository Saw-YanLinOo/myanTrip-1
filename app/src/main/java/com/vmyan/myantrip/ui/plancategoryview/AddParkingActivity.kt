package com.vmyan.myantrip.ui.plancategoryview

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.google.firebase.Timestamp
import com.vmyan.myantrip.R
import com.vmyan.myantrip.customui.switchdatetime.SwitchDateTimeDialogFragment
import com.vmyan.myantrip.ui.TripPlanActivity
import com.vmyan.myantrip.ui.viewmodel.AddPlanViewModel
import com.vmyan.myantrip.utils.Resource
import kotlinx.android.synthetic.main.activity_add_parking.*
import kotlinx.android.synthetic.main.activity_add_parking.no
import kotlinx.android.synthetic.main.activity_add_parking.titleImg
import kotlinx.android.synthetic.main.activity_add_parking.titleText
import kotlinx.android.synthetic.main.activity_add_parking.yes
import org.koin.android.ext.android.inject
import java.text.SimpleDateFormat
import java.util.*

class AddParkingActivity : AppCompatActivity() {

    private val viewModel: AddPlanViewModel by inject()

    private lateinit var tripStartDate: Timestamp
    private lateinit var tripEndDate: Timestamp
    private lateinit var tripId: String
    private var departureDate: Timestamp? = null
    private var arrivalDate: Timestamp? = null

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_parking)

        tripId = intent.getStringExtra("tripId")!!
        tripStartDate = intent.getParcelableExtra("tripStartDate")!!
        tripEndDate = intent.getParcelableExtra("tripEndDate")!!
        val planName = intent.getStringExtra("planName")
        val planImg = intent.getStringExtra("planImg")
        titleText.text = "Add $planName"
        Glide.with(this).load(planImg).into(titleImg)

        parkingbackbtn.setOnClickListener {
            onBackPressed()
            finish()
        }

        var confirm = true

        if (yes.isChecked){
            confirm = true
        }else if (no.isChecked){
            confirm = false
        }


        parkingdripoffdate.setOnClickListener {
            pickCustDate("off")
        }

        parkingpickupdate.setOnClickListener {
            pickCustDate("up")
        }

        parikingaddbtn.setOnClickListener {
            addPlan(
                tripId,
                parkingname.text.toString(),
                planImg!!,
                departureDate!!,
                parkingstate.text.toString(),
                parkingcity.text.toString(),
                parkingaddress.text.toString(),
                parkingcost.text.toString().toInt(),
                confirm,
                "",
                parkingdesc.text.toString(),
                "",
                9,
                "Drop Off"
            )
            addPlan2(
                tripId,
                parkingname.text.toString(),
                planImg,
                arrivalDate!!,
                parkingstate.text.toString(),
                parkingcity.text.toString(),
                parkingaddress.text.toString(),
                "0".toInt(),
                confirm,
                "",
                parkingdesc.text.toString(),
                "",
                9,
                "Pick Up"
            )

            val intent = Intent(this, TripPlanActivity::class.java)
            intent.putExtra("tripId",tripId)
            startActivity(intent)
        }

    }

    private fun addPlan(
        tripId: String,
        name: String,
        img: String,
        date: Timestamp,
        state: String,
        city: String,
        address: String,
        estimationCost: Int,
        confirmation: Boolean,
        type: String,
        description: String,
        details: String,
        viewType : Int,
        status: String
    ){
        viewModel.addPlan(
            tripId, name, img, date, state, city, address, estimationCost, confirmation, type, description, details, viewType, status
        ).observe(this,{
            when(it){
                is Resource.Loading ->{

                }
                is Resource.Success ->{

                }
                is Resource.Failure ->{
                    println(it.message)
                }
            }
        })
    }

    private fun addPlan2(
        tripId: String,
        name: String,
        img: String,
        date: Timestamp,
        state: String,
        city: String,
        address: String,
        estimationCost: Int,
        confirmation: Boolean,
        type: String,
        description: String,
        details: String,
        viewType : Int,
        status: String
    ){
        viewModel.addPlan(
            tripId, name, img, date, state, city, address, estimationCost, confirmation, type, description, details, viewType, status
        ).observe(this,{
            when(it){
                is Resource.Loading ->{

                }
                is Resource.Success ->{

                }
                is Resource.Failure ->{
                    println(it.message)
                }
            }
        })
    }


    private fun pickCustDate(status: String) {
        val picker = SwitchDateTimeDialogFragment.newInstance("Pick Date & Time For Your Plan", "SET", "CANCEL")
        picker.startAtCalendarView()
        picker.setTimeZone(TimeZone.getDefault())
        picker.minimumDateTime = this.tripStartDate.toDate()
        picker.maximumDateTime = this.tripEndDate.toDate()

        picker.setOnButtonClickListener(object : SwitchDateTimeDialogFragment.OnButtonClickListener{
            @SuppressLint("SimpleDateFormat")
            override fun onPositiveButtonClick(date: Date?) {
                when(status) {
                    "off" -> {
                        departureDate = Timestamp(date!!)
                        parkingdripoffdate.text = SimpleDateFormat("MMM, dd yyyy   hh:mm a").format(date)
                    }
                    "up" -> {
                        arrivalDate = Timestamp(date!!)
                        parkingpickupdate.text = SimpleDateFormat("MMM, dd yyyy   hh:mm a").format(date)
                    }
                }
            }

            override fun onNegativeButtonClick(date: Date?) {
                println()
            }

        })
        picker.show(supportFragmentManager,"pick date")
    }
}