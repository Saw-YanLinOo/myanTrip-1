package com.vmyan.myantrip.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.vmyan.myantrip.R
import com.vmyan.myantrip.model.TripWithPlan
import com.vmyan.myantrip.ui.viewmodel.TripPlanViewModel
import com.vmyan.myantrip.utils.Resource
import kotlinx.android.synthetic.main.activity_trip_plan.*
import org.koin.android.ext.android.inject
import java.text.SimpleDateFormat


class TripPlanActivity : AppCompatActivity() {

    private val viewModel: TripPlanViewModel by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_trip_plan)

        val tripId = intent.getStringExtra("tripId")

        setUpObserver(tripId!!)



    }

    @SuppressLint("SetTextI18n", "SimpleDateFormat")
    private fun setUpUI(data: TripWithPlan){
        trip_total_cost_plan.text = "MMK ${data.trip.tripCost}"
        tripName_plan.text = data.trip.tripName
        startDate_plan.text = SimpleDateFormat("MMM, dd\nyyyy").format(data.trip.tripStartDate.toDate())
        endDate_plan.text = SimpleDateFormat("MMM, dd\nyyyy").format(data.trip.tripEndDate.toDate())

        val interval = data.trip.tripEndDate.seconds - data.trip.tripStartDate.seconds
        val duration = ((interval/60)/60)/24

        duration_plan.text = "$duration days"
        destination_plan.text = data.trip.tripDestination
        tripType_plan.text = data.trip.tripType
//        Glide.with(this).load(data.trip.userImg).into(plan_userImg)
//        plan_desc.text = data.trip.tripDesc
//        plan_userName.text = data.trip.userName

        add_plan_btn.setOnClickListener {
            val intent = Intent(this, AddPlanActivity::class.java)
            intent.putExtra("tripId", data.trip.tripId)
            intent.putExtra("tripStartDate",data.trip.tripStartDate)
            intent.putExtra("tripEndDate",data.trip.tripEndDate)
            startActivity(intent)
        }
    }


    private fun setUpObserver(tripId: String){
        viewModel.getTripPlan(tripId).observe(this, {
            when(it){
                is Resource.Loading ->{

                }
                is Resource.Success -> {
                    setUpUI(it.data)
                }
                is Resource.Failure -> {

                }
            }
        })
    }



}