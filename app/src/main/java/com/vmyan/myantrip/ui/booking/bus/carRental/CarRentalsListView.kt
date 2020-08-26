package com.vmyan.myantrip.ui.booking.bus.carRental

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
import com.github.rubensousa.gravitysnaphelper.GravitySnapHelper
import com.vmyan.myantrip.R
import com.vmyan.myantrip.data.booking.carRental.CarListRepositoryImpl
import com.vmyan.myantrip.model.bus.BusListItem
import com.vmyan.myantrip.model.carRental.CarRentailsItem
import com.vmyan.myantrip.ui.adapter.carRental.CarRentaisListAdapter
import com.vmyan.myantrip.ui.bs.BusSorting
import com.vmyan.myantrip.ui.bs.CarSorting
import com.vmyan.myantrip.ui.interfaceImpl.Sorting
import com.vmyan.myantrip.ui.viewmodel.carRental.CarListVM
import com.vmyan.myantrip.ui.viewmodel.carRental.CarListVMFactory
import com.vmyan.myantrip.utils.Resource
import kotlinx.android.synthetic.main.activity_car_rentals_list_view.*

class CarRentalsListView : AppCompatActivity() , CarRentaisListAdapter.ItemClickListener, Sorting {
    private val viewModel by lazy {
        ViewModelProviders.of(
            this,
            CarListVMFactory(CarListRepositoryImpl())
        ).get(
            CarListVM::class.java
        )
    }
    private lateinit var carRentailsListAdapter: CarRentaisListAdapter
    private var carSorting = mutableListOf<CarRentailsItem>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_car_rentals_list_view)
        imgCarSorting.setOnClickListener {
            val bottomSheet= CarSorting()
            bottomSheet.show(supportFragmentManager,bottomSheet.tag)
        }
        appComImgBackClick.setOnClickListener {
            this.finish()
        }
        setAllCarRentailsList()
        setAdapter()


    }
    fun setAdapter() {
        carRentailsListAdapter = CarRentaisListAdapter(this, mutableListOf())
        rv_carRentailsList.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rv_carRentailsList.apply {
            setHasFixedSize(true)
            setItemViewCacheSize(20)
            rv_carRentailsList.adapter = carRentailsListAdapter
            ViewCompat.setNestedScrollingEnabled(rv_carRentailsList, false)
        }
    }
    @SuppressLint("ShowToast")
    private fun setAllCarRentailsList() {
        //activity
        viewModel.fetchCarList().observe(this, Observer {
            when (it) {
                is Resource.Loading -> {
                    println("loading.....PlaceCategoryList")
                }
                is Resource.Success -> {
                    carRentailsListAdapter.setItems(it.data)
                    carSorting.addAll(it.data)

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
       val intent =Intent(this, CarRentalStepper::class.java)
        startActivity(intent)
    }

    override fun lowestPrice(lPrice: String) {
      carRentailsListAdapter.setItems(lowRPlaceList(carSorting))
    }

    override fun highestPrice(hPrice: String) {
        carRentailsListAdapter.setItems(highRPlaceList(carSorting))
    }
    private fun highRPlaceList(list: MutableList<CarRentailsItem>): MutableList<CarRentailsItem>{
        list.sortWith(Comparator { p0, p1 ->
            var res = -1
            if (p0!!.pricePerDay < p1!!.pricePerDay) {
                res = 1
            }
            res
        })

        return list
    }

    private fun lowRPlaceList(list: MutableList<CarRentailsItem>): MutableList<CarRentailsItem>{
        list.sortWith(Comparator { p0, p1 ->
            var res = -1
            if (p0!!.pricePerDay> p1!!.pricePerDay) {
                res = 1
            }
            res
        })

        return list
    }
}