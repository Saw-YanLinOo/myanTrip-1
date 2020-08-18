package com.vmyan.myantrip.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.SnapHelper
import com.github.rubensousa.gravitysnaphelper.GravitySnapHelper
import com.vmyan.myantrip.R
import com.vmyan.myantrip.model.PlanCategory
import com.vmyan.myantrip.ui.adapter.PlanCategoryListAdapter
import com.vmyan.myantrip.ui.adapter.SearchPlaceListAdapter
import com.vmyan.myantrip.ui.plancategoryview.AddHotelActivity
import kotlinx.android.synthetic.main.activity_add_plan.*
import kotlinx.android.synthetic.main.activity_search_place.*

class AddPlanActivity : AppCompatActivity(), PlanCategoryListAdapter.ItemClickListener {

    private lateinit var planCategoryListAdapter: PlanCategoryListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_plan)

        setUpSearchResultRecycler()
        loadData()
    }

    private fun setUpSearchResultRecycler(){
        planCategoryListAdapter = PlanCategoryListAdapter(this, mutableListOf())
        plancategory_recycler.layoutManager = GridLayoutManager(this, 3,GridLayoutManager.VERTICAL,false)
        plancategory_recycler.apply {
            setHasFixedSize(true)
            setItemViewCacheSize(20)
        }

        val snapHelperStart: SnapHelper = GravitySnapHelper(Gravity.TOP)
        snapHelperStart.attachToRecyclerView(plancategory_recycler)
        plancategory_recycler.isNestedScrollingEnabled = false
        plancategory_recycler.adapter = planCategoryListAdapter

    }

    private fun loadData(){
        val list = mutableListOf<PlanCategory>()

        list.add(PlanCategory("Hotel", "https://firebasestorage.googleapis.com/v0/b/myantrip-45671.appspot.com/o/PlanCategory%2Fhotel.png?alt=media&token=c272ea5e-fa34-4987-9b69-2ecbc956af7d"))
        list.add(PlanCategory("Bus", "https://firebasestorage.googleapis.com/v0/b/myantrip-45671.appspot.com/o/PlanCategory%2Fbus.png?alt=media&token=ed0b346c-3580-426b-aceb-b1b1fcc85377"))
        list.add(PlanCategory("Train", "https://firebasestorage.googleapis.com/v0/b/myantrip-45671.appspot.com/o/PlanCategory%2Ftrain.png?alt=media&token=697d27f6-eeec-434d-8220-f1e5c2e1c181"))
        list.add(PlanCategory("Car Rental", "https://firebasestorage.googleapis.com/v0/b/myantrip-45671.appspot.com/o/PlanCategory%2Fcarrental.png?alt=media&token=c0d72d6a-a0df-4f8d-a0e7-3a070abd9573"))

        planCategoryListAdapter.setItems(list)
    }

    override fun onPlaceClick(name: String, img: String) {
        when(name){
            "Hotel" -> {
                val i = Intent(this, AddHotelActivity::class.java)
                startActivity(i)
            }
        }
    }


}