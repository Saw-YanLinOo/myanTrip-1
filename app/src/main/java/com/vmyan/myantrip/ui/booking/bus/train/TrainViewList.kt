package com.vmyan.myantrip.ui.booking.bus.train

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
import com.vmyan.myantrip.data.booking.carRental.train.TrainRepositoryImpl
import com.vmyan.myantrip.model.PlaceDetails
import com.vmyan.myantrip.model.bookingCate.TownListItem
import com.vmyan.myantrip.model.hotel.RoomList
import com.vmyan.myantrip.model.train.TrainListItem
import com.vmyan.myantrip.ui.adapter.train.TrainListAdapter
import com.vmyan.myantrip.ui.bs.SelectRoomGuests
import com.vmyan.myantrip.ui.bs.Sorting_BS
import com.vmyan.myantrip.ui.interfaceImpl.Sorting
import com.vmyan.myantrip.ui.viewmodel.train.TrainVM
import com.vmyan.myantrip.ui.viewmodel.train.TrainVMFactory
import com.vmyan.myantrip.utils.Resource
import kotlinx.android.synthetic.main.activity_train_booking.*
import kotlinx.android.synthetic.main.activity_train_view_list.*


class TrainViewList : AppCompatActivity(), TrainListAdapter.ItemClickListener, Sorting {
    private val viewModel by lazy {
        ViewModelProviders.of(
            this,
            TrainVMFactory(TrainRepositoryImpl())
        ).get(
            TrainVM::class.java
        )
    }
    private lateinit var trainListAdapter: TrainListAdapter
    private  var trainListSorting = mutableListOf<TrainListItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_train_view_list)
        Glide.with(this).load(intent.getStringExtra("TrainImgFrom")).into(imgTrainViewFrom)
        Glide.with(this).load(intent.getStringExtra("TrainImgTo")).into(imgTrainViewTo)
        txt_TrainViewFrom.text=intent.getStringExtra("TrainTxtFrom")
        txt_TrainViewTo.text=intent.getStringExtra("TrainTxtTo")
        trainDepartDateView.text=intent.getStringExtra("TrainDepartDate")
        setTrainAllList()
        setAdapter()
        img_sortTrainList.setOnClickListener {
            val bottomSheet= Sorting_BS()
            bottomSheet.show(supportFragmentManager,bottomSheet.tag)
        }
    }

    fun setAdapter() {
        trainListAdapter = TrainListAdapter(this, mutableListOf())
        rv_TrainList.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rv_TrainList.apply {
            setHasFixedSize(true)
            setItemViewCacheSize(20)
            rv_TrainList.adapter = trainListAdapter
            ViewCompat.setNestedScrollingEnabled(rv_TrainList, false)
        }
    }
    @SuppressLint("ShowToast")
    private fun setTrainAllList() {
        //activity
        viewModel.fetchTrainList().observe(this, Observer {
            when (it) {
                is Resource.Loading -> {
                    println("loading.....PlaceCategoryList")
                }
                is Resource.Success -> {
                    trainListAdapter.setItems(it.data)
                    trainListSorting.addAll(it.data)

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
        val intent =Intent(this, TrainStepper::class.java)
        startActivity(intent)
    }

    override fun lowestPrice(lPrice: String) {
        trainListAdapter.setItems(lowRPlaceList(trainListSorting))

    }

    override fun highestPrice(hPrice: String) {
            trainListAdapter.setItems(highRPlaceList(trainListSorting))
    }
    private fun highRPlaceList(list: MutableList<TrainListItem>): MutableList<TrainListItem>{
        list.sortWith(Comparator { p0, p1 ->
            var res = -1
            if (p0!!.trainPerSeatPrice < p1!!.trainPerSeatPrice) {
                res = 1
            }
            res
        })

        return list
    }

    private fun lowRPlaceList(list: MutableList<TrainListItem>): MutableList<TrainListItem>{
        list.sortWith(Comparator { p0, p1 ->
            var res = -1
            if (p0!!.trainPerSeatPrice> p1!!.trainPerSeatPrice) {
                res = 1
            }
            res
        })

        return list
    }
}