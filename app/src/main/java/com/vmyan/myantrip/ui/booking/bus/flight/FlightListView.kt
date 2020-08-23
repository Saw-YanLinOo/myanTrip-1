package com.vmyan.myantrip.ui.booking.bus.flight

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.widget.Toast
import androidx.core.view.ViewCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.SnapHelper
import com.bumptech.glide.Glide
import com.github.rubensousa.gravitysnaphelper.GravitySnapHelper
import com.vmyan.myantrip.R
import com.vmyan.myantrip.data.booking.carRental.flight.FlightListRepositoryImpl
import com.vmyan.myantrip.ui.adapter.flight.FlightListAdapeter
import com.vmyan.myantrip.ui.viewmodel.flight.FlightListVM
import com.vmyan.myantrip.ui.viewmodel.flight.FlightListVMFactory
import com.vmyan.myantrip.utils.Resource
import kotlinx.android.synthetic.main.activity_flight_list_view.*

class FlightListView : AppCompatActivity(), FlightListAdapeter.ItemClickListener{
    private val viewModel by lazy {
        ViewModelProviders.of(
            this,
            FlightListVMFactory(FlightListRepositoryImpl())
        ).get(
            FlightListVM::class.java
        )
    }
    private lateinit var flightListAdaper: FlightListAdapeter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_flight_list_view)
        txtAdultsCount.text=intent.getStringExtra("AdultsCount")
        txtChildCount.text=intent.getStringExtra("ChildCount")
        txtInfantCount.text=intent.getStringExtra("InfantCount")

        txt_FlightViewFrom.text=intent.getStringExtra("FlightFromTxt")
        txt_FlightViewTo.text=intent.getStringExtra("FlightToTxt")
        departFlightDate.text=intent.getStringExtra("FlightDepartDate")

        Glide.with(this).load(intent.getStringExtra("ImgFlightFrom")).into(imgFlightViewFrom)
        Glide.with(this).load(intent.getStringExtra("ImgFlightTo")).into(imgFlightViewTo)


        setAllFlightList()
        setAdapter()
    }

    fun setAdapter() {
        flightListAdaper = FlightListAdapeter(this, mutableListOf())
        rv_flightList.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rv_flightList.apply {
            setHasFixedSize(true)
            setItemViewCacheSize(20)
            val snapHelperStart: SnapHelper = GravitySnapHelper(Gravity.START)
            snapHelperStart.attachToRecyclerView(rv_flightList)

            rv_flightList.adapter = flightListAdaper
            ViewCompat.setNestedScrollingEnabled(rv_flightList, false)
        }
    }

    @SuppressLint("ShowToast")
    private fun setAllFlightList() {
        //activity
        viewModel.fetchFlightList().observe(this, Observer {
            when (it) {
                is Resource.Loading -> {
                    println("loading.....PlaceCategoryList")
                }
                is Resource.Success -> {
                    flightListAdaper.setItems(it.data)

                }
                is Resource.Failure -> {
                    println(it.message)
                    Toast.makeText(
                        this,
                        "An error is ocurred:${it.message}",
                        Toast.LENGTH_SHORT
                    )
                }
            }
        })
    }

    override fun onItemClick(id: String) {
        val intent =Intent(this, FlightStepper::class.java)
        startActivity(intent)

    }
}