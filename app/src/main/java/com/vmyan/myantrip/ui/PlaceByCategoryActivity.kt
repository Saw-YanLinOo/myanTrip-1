package com.vmyan.myantrip.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.SnapHelper
import com.github.rubensousa.gravitysnaphelper.GravitySnapHelper
import com.vmyan.myantrip.R
import com.vmyan.myantrip.ui.adapter.PCSubCategoryListAdapter
import com.vmyan.myantrip.ui.viewmodel.PlaceByCategoryVMFactory
import com.vmyan.myantrip.ui.viewmodel.PlaceByCategoryViewModel
import com.vmyan.myantrip.utils.Resource
import kotlinx.android.synthetic.main.activity_place_by_category.*
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.android.closestDI
import org.kodein.di.instance

class PlaceByCategoryActivity : AppCompatActivity(), DIAware {

    override val di: DI by closestDI()
    private val viewModelFactory : PlaceByCategoryVMFactory by instance()

    private lateinit var viewModel: PlaceByCategoryViewModel
    private lateinit var pcSubCategoryListAdapter: PCSubCategoryListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_place_by_category)
        viewModel = ViewModelProvider(this, viewModelFactory).get(PlaceByCategoryViewModel::class.java)

        val id = intent.getStringExtra("cat_id")
        val name = intent.getStringExtra("cat_name")
        pc_title.text = name
        setUpSubPlaceCategoryRecycler()
        if (id != null) {
            setUpObserver(id)
        }
    }


    private fun setUpSubPlaceCategoryRecycler(){
        pcSubCategoryListAdapter = PCSubCategoryListAdapter(this,mutableListOf())
        pc_recycler.layoutManager = LinearLayoutManager(this,
            LinearLayoutManager.VERTICAL,false)
        pc_recycler.apply {
            setHasFixedSize(true)
            setItemViewCacheSize(20)
        }

        val snapHelperStart: SnapHelper = GravitySnapHelper(Gravity.TOP)
        snapHelperStart.attachToRecyclerView(pc_recycler)
        pc_recycler.isNestedScrollingEnabled = false
        pc_recycler.adapter = pcSubCategoryListAdapter
    }

    private fun setUpObserver(id: String) {
        viewModel.fetchPlaceByCategory(id).observe(this, Observer {
            when (it) {
                is Resource.Loading -> {
                    placebycategory_placeholder.startShimmer()
                    placebycategory_placeholder.visibility = View.VISIBLE
                    pc_recycler.visibility = View.GONE
                }
                is Resource.Success -> {
                    placebycategory_placeholder.stopShimmer()
                    placebycategory_placeholder.visibility = View.GONE
                    pc_recycler.visibility = View.VISIBLE
                    pcSubCategoryListAdapter.setItems(it.data)
                }
                is Resource.Failure -> {
                    placebycategory_placeholder.stopShimmer()
                    placebycategory_placeholder.visibility = View.GONE
                    pc_recycler.visibility = View.GONE
                    println(it.throwable.message)
                    Toast.makeText(
                        this,
                        "An error is ocurred:${it.throwable.message}",
                        Toast.LENGTH_SHORT
                    )
                }
            }
        })
    }

    override fun onResume() {
        super.onResume()
        placebycategory_placeholder.startShimmer()
    }

    override fun onPause() {
        super.onPause()
        placebycategory_placeholder.stopShimmer()
    }
}