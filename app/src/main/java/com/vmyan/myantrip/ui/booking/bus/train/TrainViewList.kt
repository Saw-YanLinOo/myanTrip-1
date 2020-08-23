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
import com.github.rubensousa.gravitysnaphelper.GravitySnapHelper
import com.vmyan.myantrip.R
import com.vmyan.myantrip.data.booking.carRental.train.TrainRepositoryImpl
import com.vmyan.myantrip.ui.adapter.train.TrainListAdapter
import com.vmyan.myantrip.ui.viewmodel.train.TrainVM
import com.vmyan.myantrip.ui.viewmodel.train.TrainVMFactory
import com.vmyan.myantrip.utils.Resource
import kotlinx.android.synthetic.main.activity_train_view_list.*


class TrainViewList : AppCompatActivity(), TrainListAdapter.ItemClickListener {
    private val viewModel by lazy {
        ViewModelProviders.of(
            this,
            TrainVMFactory(TrainRepositoryImpl())
        ).get(
            TrainVM::class.java
        )
    }
    private lateinit var trainListAdapter: TrainListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_train_view_list)
        setTrainAllList()
        setAdapter()
    }

    fun setAdapter() {
        trainListAdapter = TrainListAdapter(this, mutableListOf())
        rv_TrainList.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rv_TrainList.apply {
            setHasFixedSize(true)
            setItemViewCacheSize(20)
            val snapHelperStart: SnapHelper = GravitySnapHelper(Gravity.START)
            snapHelperStart.attachToRecyclerView(rv_TrainList)

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
}