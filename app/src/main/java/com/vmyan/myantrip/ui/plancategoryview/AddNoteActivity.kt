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
import kotlinx.android.synthetic.main.activity_add_note.*
import kotlinx.android.synthetic.main.activity_add_note.titleImg
import kotlinx.android.synthetic.main.activity_add_note.titleText
import org.koin.android.ext.android.inject
import java.text.SimpleDateFormat
import java.util.*

class AddNoteActivity : AppCompatActivity() {

    private val viewModel: AddPlanViewModel by inject()

    private lateinit var tripStartDate: Timestamp
    private lateinit var tripEndDate: Timestamp
    private lateinit var tripId: String
    private var checkInDate: Timestamp? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_note)

        notebackbtn.setOnClickListener {
            onBackPressed()
            finish()
        }


        tripId = intent.getStringExtra("tripId")!!
        tripStartDate = intent.getParcelableExtra("tripStartDate")!!
        tripEndDate = intent.getParcelableExtra("tripEndDate")!!
        val planName = intent.getStringExtra("planName")
        val planImg = intent.getStringExtra("planImg")
        titleText.text = "Add $planName"
        Glide.with(this).load(planImg).into(titleImg)

        notedate.setOnClickListener {
            pickCustDate()
        }

        noteaddbtn.setOnClickListener {
            addPlan(
                tripId,
                notetitle.text.toString(),
                planImg!!,
                checkInDate!!,
                notestate.text.toString(),
                notecity.text.toString(),
                noteaddress.text.toString(),
                0,
                true,
                "",
                "",
                notedetails.text.toString(),
                7,
                ""
            )
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
        viewType: Int,
        status: String
    ){
        viewModel.addPlan(
            tripId,
            name,
            img,
            date,
            state,
            city,
            address,
            estimationCost,
            confirmation,
            type,
            description,
            details,
            viewType,
            status
        ).observe(this, {
            when (it) {
                is Resource.Loading -> {

                }
                is Resource.Success -> {
                    val intent = Intent(this, TripPlanActivity::class.java)
                    intent.putExtra("tripId", tripId)
                    startActivity(intent)
                }
                is Resource.Failure -> {

                }
            }
        })
    }


    private fun pickCustDate() {
        val picker = SwitchDateTimeDialogFragment.newInstance("Pick Date & Time For Your Plan", "SET", "CANCEL")
        picker.startAtCalendarView()
        picker.setTimeZone(TimeZone.getDefault())
        picker.minimumDateTime = this.tripStartDate.toDate()
        picker.maximumDateTime = this.tripEndDate.toDate()

        picker.setOnButtonClickListener(object : SwitchDateTimeDialogFragment.OnButtonClickListener{
            @SuppressLint("SimpleDateFormat")
            override fun onPositiveButtonClick(date: Date?) {
                checkInDate = Timestamp(date!!)
                notedate.text = SimpleDateFormat("MMM, dd yyyy   hh:mm a").format(date)
            }

            override fun onNegativeButtonClick(date: Date?) {
                println()
            }

        })
        picker.show(supportFragmentManager,"pick date")
    }
}