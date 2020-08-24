package com.vmyan.myantrip.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.Gravity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.SnapHelper
import com.github.rubensousa.gravitysnaphelper.GravitySnapHelper
import com.vmyan.myantrip.R
import com.vmyan.myantrip.model.Emergency
import com.vmyan.myantrip.model.Place
import com.vmyan.myantrip.ui.adapter.EmergencyListAdapter
import com.vmyan.myantrip.ui.adapter.TripPlanUserListAdapter
import com.vmyan.myantrip.ui.viewmodel.EmergencyViewModel
import com.vmyan.myantrip.utils.LoadingDialog
import com.vmyan.myantrip.utils.Resource
import kotlinx.android.synthetic.main.activity_emergency_list.*
import kotlinx.android.synthetic.main.activity_trip_plan_user.*
import org.koin.android.ext.android.inject
import java.util.*

class EmergencyListActivity : AppCompatActivity() {

    private val viewModel: EmergencyViewModel by inject()

    private lateinit var emergencyListAdapter: EmergencyListAdapter
    private val loadingDialog = LoadingDialog(this)
    private val eList = mutableListOf<Emergency>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_emergency_list)

        emergency_backbtn.setOnClickListener {
            onBackPressed()
            finish()
        }

        emergency_search_input.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                println()
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                searchPlaces(emergency_search_input.text.toString())
            }

            override fun afterTextChanged(p0: Editable?) {
                println()
            }

        })

        setUpList()
        getData()
    }

    private fun searchPlaces(nameS: String) {
        var name = nameS
        if (name.isNotEmpty()) name =
            name.substring(0, 1).toUpperCase(Locale.ROOT) + name.substring(1).toLowerCase(Locale.ROOT)
        val results: MutableList<Emergency> = mutableListOf()
        for (user in eList) {
            if (user.city.contains(name)) {
                results.add(user)
            }
        }
        emergencyListAdapter.setItems(results)
    }

    private fun setUpList(){
        emergencyListAdapter = EmergencyListAdapter(mutableListOf())
        emergency_recycler.layoutManager = LinearLayoutManager(this,
            LinearLayoutManager.VERTICAL, false)
        emergency_recycler.apply {
            setHasFixedSize(true)
            setItemViewCacheSize(20)
        }

        val snapHelperStart: SnapHelper = GravitySnapHelper(Gravity.TOP)
        snapHelperStart.attachToRecyclerView(emergency_recycler)
        emergency_recycler.isNestedScrollingEnabled = false
        emergency_recycler.adapter = emergencyListAdapter
    }

    private fun getData(){
        viewModel.getEmergencyList().observe(this, {
            when(it) {
                is Resource.Loading -> loadingDialog.startLoading()
                is Resource.Success -> {
//                    emergencyListAdapter.setItems(it.data)
                    eList.addAll(it.data)
                    searchPlaces(emergency_search_input.text.toString())
                    loadingDialog.stopLoading()
                }
                is Resource.Failure -> {
                    loadingDialog.stopLoading()
                    println("error")
                }
            }
        })
    }


}