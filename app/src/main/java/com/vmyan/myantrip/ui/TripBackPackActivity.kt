package com.vmyan.myantrip.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.SnapHelper
import com.github.rubensousa.gravitysnaphelper.GravitySnapHelper
import com.vmyan.myantrip.R
import com.vmyan.myantrip.ui.adapter.BackpackListAdapter
import com.vmyan.myantrip.ui.viewmodel.TripBackPackViewModel
import com.vmyan.myantrip.utils.LoadingDialog
import com.vmyan.myantrip.utils.Resource
import kotlinx.android.synthetic.main.activity_trip_back_pack.*
import org.koin.android.ext.android.inject

class TripBackPackActivity : AppCompatActivity(), BackpackListAdapter.ItemClickListener {

    private val viewModel: TripBackPackViewModel by inject()

    private lateinit var tripId: String
    private lateinit var backpackListAdapter: BackpackListAdapter
    private val loadingDialog = LoadingDialog(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_trip_back_pack)

        tripId = intent.getStringExtra("tripId")!!

        backbtn.setOnClickListener {
            onBackPressed()
            finish()
        }
        setUpList()
        loadData()

        addbtn.setOnClickListener {
            addData()
            itemnameinput.setText("")
            countinput.setText("")
        }


    }

    private fun setUpList(){
        backpackListAdapter = BackpackListAdapter(this, mutableListOf())
        backpackrecycler.layoutManager = LinearLayoutManager(this,
            LinearLayoutManager.VERTICAL, false)
        backpackrecycler.apply {
            setHasFixedSize(true)
            setItemViewCacheSize(20)
        }

        val snapHelperStart: SnapHelper = GravitySnapHelper(Gravity.TOP)
        snapHelperStart.attachToRecyclerView(backpackrecycler)
        backpackrecycler.isNestedScrollingEnabled = false
        backpackrecycler.adapter = backpackListAdapter
    }

    private fun loadData(){
        viewModel.getBackpackList(this.tripId).observe(this,{
            when(it) {
                is Resource.Loading -> {
                    loadingDialog.startLoading()
                }
                is Resource.Success -> {
                    backpackListAdapter.setItems(it.data)
                    loadingDialog.stopLoading()
                }
                is Resource.Failure -> {
                    loadingDialog.stopLoading()
                }
            }
        })
    }

    private fun addData() {
        viewModel.addBackpack(tripId,itemnameinput.text.toString(),countinput.text.toString().toInt()).observe(this,{
            when(it) {
                is Resource.Loading -> {
                    loadingDialog.startLoading()
                }
                is Resource.Success -> {
                    loadData()
                    loadingDialog.stopLoading()
                }
                is Resource.Failure -> {
                    loadingDialog.stopLoading()
                }
            }
        })
    }

    override fun onItemClick(id: String) {
        viewModel.updateCheck(tripId,id,true).observe(this,{
            when(it) {
                is Resource.Loading -> {
                    loadingDialog.startLoading()
                }
                is Resource.Success -> {
                    loadData()
                    loadingDialog.stopLoading()
                }
                is Resource.Failure -> {
                    loadingDialog.stopLoading()
                }
            }
        })
    }
}