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
import kotlinx.android.synthetic.main.activity_add_train.*
import kotlinx.android.synthetic.main.activity_add_train.no
import kotlinx.android.synthetic.main.activity_add_train.titleImg
import kotlinx.android.synthetic.main.activity_add_train.titleText
import kotlinx.android.synthetic.main.activity_add_train.yes
import org.koin.android.ext.android.inject
import java.text.SimpleDateFormat
import java.util.*

class AddTrainActivity : AppCompatActivity() {

    private val viewModel: AddPlanViewModel by inject()

    private lateinit var tripStartDate: Timestamp
    private lateinit var tripEndDate: Timestamp
    private lateinit var tripId: String
    private var departureDate: Timestamp? = null
    private var arrivalDate: Timestamp? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_train)

        tripId = intent.getStringExtra("tripId")!!
        tripStartDate = intent.getParcelableExtra("tripStartDate")!!
        tripEndDate = intent.getParcelableExtra("tripEndDate")!!
        val planName = intent.getStringExtra("planName")
        val planImg = intent.getStringExtra("planImg")
        titleText.text = "Add $planName"
        Glide.with(this).load(planImg).into(titleImg)

        busbackbtn.setOnClickListener {
            onBackPressed()
            finish()
        }

        busdeparturedate.setOnClickListener {
            pickCustDate("departure")
        }

        busarrivaldate.setOnClickListener {
            pickCustDate("arrival")
        }

        var confirm = true

        if (yes.isChecked){
            confirm = true
        }else if (no.isChecked){
            confirm = false
        }

        busaddbtn.setOnClickListener {
            addPlan(
                tripId,
                busname.text.toString(),
                planImg!!,
                departureDate!!,
                busfromstate.text.toString(),
                busfromcity.text.toString(),
                busfromaddress.text.toString(),
                buscost.text.toString().toInt(),
                confirm,
                bustype.text.toString(),
                "",
                "",
                5,
                "Departure"
            )
            addPlan2(
                tripId,
                busname.text.toString(),
                planImg,
                arrivalDate!!,
                bustostate.text.toString(),
                bustocity.text.toString(),
                bustoaddress.text.toString(),
                "0".toInt(),
                confirm,
                bustype.text.toString(),
                "",
                "",
                5,
                "Arrival"
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
                    "departure" -> {
                        departureDate = Timestamp(date!!)
                        busdeparturedate.text = SimpleDateFormat("MMM, dd yyyy   hh:mm a").format(date)
                    }
                    "arrival" -> {
                        arrivalDate = Timestamp(date!!)
                        busarrivaldate.text = SimpleDateFormat("MMM, dd yyyy   hh:mm a").format(date)
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