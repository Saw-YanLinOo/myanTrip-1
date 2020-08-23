package com.vmyan.myantrip.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.SnapHelper
import com.github.rubensousa.gravitysnaphelper.GravitySnapHelper
import com.vmyan.myantrip.R
import com.vmyan.myantrip.ui.adapter.BackpackListAdapter
import com.vmyan.myantrip.ui.adapter.TripPlanUserListAdapter
import com.vmyan.myantrip.ui.viewmodel.TripPlanUserViewModel
import com.vmyan.myantrip.utils.LoadingDialog
import com.vmyan.myantrip.utils.Resource
import kotlinx.android.synthetic.main.activity_trip_back_pack.*
import kotlinx.android.synthetic.main.activity_trip_plan.*
import kotlinx.android.synthetic.main.activity_trip_plan_user.*
import kotlinx.android.synthetic.main.trip_plan_users_item_layout.*
import org.koin.android.ext.android.inject

class TripPlanUserActivity : AppCompatActivity() {

    private val viewModel: TripPlanUserViewModel by inject()

    private lateinit var tripId: String
    private val loadingDialog = LoadingDialog(this)
    private lateinit var tripPlanUserListAdapter: TripPlanUserListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_trip_plan_user)

        tripId = intent.getStringExtra("tripId")!!
        setUpList()
        getData()

        tripplanuser_backbtn.setOnClickListener {
            onBackPressed()
            finish()
        }

        fab_adduser.setOnClickListener {
            if (teammateuseremailinput.text.toString() != null || teammateuseremailinput.text.toString() != ""){
                teammateuseremailinput.setText("")
                addData()
            }
        }

    }

    private fun setUpList(){
        tripPlanUserListAdapter = TripPlanUserListAdapter(mutableListOf())
        teammaterecycler.layoutManager = LinearLayoutManager(this,
            LinearLayoutManager.VERTICAL, false)
        teammaterecycler.apply {
            setHasFixedSize(true)
            setItemViewCacheSize(20)
        }

        val snapHelperStart: SnapHelper = GravitySnapHelper(Gravity.TOP)
        snapHelperStart.attachToRecyclerView(teammaterecycler)
        teammaterecycler.isNestedScrollingEnabled = false
        teammaterecycler.adapter = tripPlanUserListAdapter
    }

    private fun getData(){
        viewModel.getTeammates(tripId).observe(this, {
            when(it) {
                is Resource.Loading -> loadingDialog.startLoading()
                is Resource.Success -> {
                    tripPlanUserListAdapter.setItems(it.data)
                    loadingDialog.stopLoading()
                }
                is Resource.Failure -> {
                    loadingDialog.stopLoading()
                    println("error")
                }
            }
        })
    }

    private fun addData() {
        viewModel.addTeammates(tripId,teammateuseremailinput.text.toString()).observe(this, {
            when(it) {
                is Resource.Loading -> loadingDialog.startLoading()
                is Resource.Success -> {
                    getData()
                    loadingDialog.stopLoading()
                }
                is Resource.Failure -> {
                    loadingDialog.stopLoading()
                    println("user not exist!")
                }
            }
        })
    }
}