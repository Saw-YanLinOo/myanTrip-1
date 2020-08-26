package com.vmyan.myantrip.ui.booking.bus

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
import com.vmyan.myantrip.data.booking.carRental.bus.BusListRepositoryImpl
import com.vmyan.myantrip.model.bus.BusListItem
import com.vmyan.myantrip.model.flight.FlightListItem
import com.vmyan.myantrip.ui.adapter.bus.BusListAdapter
import com.vmyan.myantrip.ui.bs.BusSorting
import com.vmyan.myantrip.ui.bs.FlightSorting
import com.vmyan.myantrip.ui.interfaceImpl.Sorting
import com.vmyan.myantrip.ui.viewmodel.bus.BusListVMFactory
import com.vmyan.myantrip.ui.viewmodel.bus.BusListVm
import com.vmyan.myantrip.utils.Resource
import kotlinx.android.synthetic.main.activity_bus_list_view.*

class BusListView : AppCompatActivity() , BusListAdapter.ItemClickListener, Sorting {
    private val viewModel by lazy {
        ViewModelProviders.of(
            this,
            BusListVMFactory(BusListRepositoryImpl())
        ).get(
            BusListVm::class.java
        )
    }
    private  var busListSorting = mutableListOf<BusListItem>()
    private lateinit var busListAdapter: BusListAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bus_list_view)
        img_sortBusList.setOnClickListener {
            val bottomSheet= BusSorting()
            bottomSheet.show(supportFragmentManager,bottomSheet.tag)
        }
        txt_BusViewFrom.text = intent.getStringExtra("BusForm")
        txt_BusViewTo.text =intent.getStringExtra("BusTo")
        Glide.with(this).load(intent.getStringExtra("BusImageFrom")).into(imgBusViewFrom)
        Glide.with(this).load(intent.getStringExtra("BusCityImageTo")).into(imgBusViewTo)
        busDepartDateView.text=intent.getStringExtra("BusDepartsDate")
        seatTotalNo.text=intent.getStringExtra("SeatTotalNo").toString()


       setBusAllList()
        setAdapter()

    }
    fun setAdapter() {
        busListAdapter = BusListAdapter(this, mutableListOf())
        rv_busList.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rv_busList.apply {
            setHasFixedSize(true)
            setItemViewCacheSize(20)
            val snapHelperStart: SnapHelper = GravitySnapHelper(Gravity.START)
            snapHelperStart.attachToRecyclerView(rv_busList)

            rv_busList.adapter = busListAdapter
            ViewCompat.setNestedScrollingEnabled(rv_busList, false)
        }
    }

    @SuppressLint("ShowToast")
    private fun setBusAllList() {
        //activity
        viewModel.fetchBusAllList().observe(this, Observer {
            when (it) {
                is Resource.Loading -> {
                    println("loading.....PlaceCategoryList")
                }
                is Resource.Success -> {
                    println(it.data)
                    busListAdapter.setItems(it.data)
                    busListSorting.addAll(it.data)

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
        val intent=Intent(this, BusStepper::class.java)
        startActivity(intent)
    }

    override fun lowestPrice(lPrice: String) {
        busListAdapter.setItems(lowRPlaceList(busListSorting))
    }

    override fun highestPrice(hPrice: String) {
        busListAdapter.setItems(highRPlaceList(busListSorting))
    }
    private fun highRPlaceList(list: MutableList<BusListItem>): MutableList<BusListItem>{
        list.sortWith(Comparator { p0, p1 ->
            var res = -1
            if (p0!!.busPricePerSeat < p1!!.busPricePerSeat) {
                res = 1
            }
            res
        })

        return list
    }

    private fun lowRPlaceList(list: MutableList<BusListItem>): MutableList<BusListItem>{
        list.sortWith(Comparator { p0, p1 ->
            var res = -1
            if (p0!!.busPricePerSeat> p1!!.busPricePerSeat) {
                res = 1
            }
            res
        })

        return list
    }
}