package com.vmyan.myantrip.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.SnapHelper
import com.bumptech.glide.Glide
import com.github.rubensousa.gravitysnaphelper.GravitySnapHelper
import com.vmyan.myantrip.R
import com.vmyan.myantrip.model.TripPlan
import com.vmyan.myantrip.model.TripWithPlan
import com.vmyan.myantrip.ui.adapter.PlanListAdapter
import com.vmyan.myantrip.ui.viewmodel.TripPlanViewModel
import com.vmyan.myantrip.utils.Resource
import kotlinx.android.synthetic.main.activity_trip_plan.*
import org.koin.android.ext.android.inject
import java.text.SimpleDateFormat


class TripPlanActivity : AppCompatActivity(),PlanListAdapter.ItemClickListener {

    private val viewModel: TripPlanViewModel by inject()

    private lateinit var planListAdapter: PlanListAdapter
    private var planList = mutableListOf<TripPlan>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_trip_plan)

        val tripId = intent.getStringExtra("tripId")

        setUpPlanListRecycler()
        setUpObserver(tripId!!)

    }

    @SuppressLint("SetTextI18n", "SimpleDateFormat")
    private fun setUpUI(data: TripWithPlan){
        trip_total_cost_plan.text = "${data.trip.tripCost} MMK"
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

    private fun setUpPlanListRecycler(){
        planListAdapter = PlanListAdapter(this, mutableListOf())
        planList_recycler.layoutManager = LinearLayoutManager(this,
            LinearLayoutManager.VERTICAL, false)
        planList_recycler.apply {
            setHasFixedSize(true)
            setItemViewCacheSize(20)
        }

        val snapHelperStart: SnapHelper = GravitySnapHelper(Gravity.TOP)
        snapHelperStart.attachToRecyclerView(planList_recycler)
        planList_recycler.isNestedScrollingEnabled = false
        planList_recycler.adapter = planListAdapter

    }


    private fun setUpObserver(tripId: String){
        viewModel.getTripPlan(tripId).observe(this, {
            when(it){
                is Resource.Loading ->{

                }
                is Resource.Success -> {
                    setUpUI(it.data)
                    planList.addAll(it.data.planList)
                    planListAdapter.setItems(it.data.planList)

                }
                is Resource.Failure -> {

                }
            }
        })
    }

    override fun onPlaceClick(plan_id: String) {
        println(plan_id)
    }


}