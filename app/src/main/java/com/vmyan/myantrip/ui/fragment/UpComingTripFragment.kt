package com.vmyan.myantrip.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.SnapHelper
import com.github.rubensousa.gravitysnaphelper.GravitySnapHelper
import com.vmyan.myantrip.R
import com.vmyan.myantrip.ui.TripPlanActivity
import com.vmyan.myantrip.ui.adapter.SearchPlaceListAdapter
import com.vmyan.myantrip.ui.adapter.UpComingTripListAdapter
import com.vmyan.myantrip.ui.viewmodel.AddNewTripViewModel
import com.vmyan.myantrip.ui.viewmodel.UpComingTripVMFactory
import com.vmyan.myantrip.ui.viewmodel.UpComingTripViewModel
import com.vmyan.myantrip.utils.Resource
import kotlinx.android.synthetic.main.activity_search_place.*
import kotlinx.android.synthetic.main.fragment_up_coming_trip.view.*
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.android.x.closestDI
import org.kodein.di.instance

class UpComingTripFragment : Fragment(), UpComingTripListAdapter.ItemClickListener, DIAware {

    override val di: DI by closestDI()

    private val viewModelFactory : UpComingTripVMFactory by instance()

    private lateinit var viewModel: UpComingTripViewModel
    private lateinit var upComingTripListAdapter: UpComingTripListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this, viewModelFactory).get(UpComingTripViewModel::class.java)
        val view = inflater.inflate(R.layout.fragment_up_coming_trip, container, false)

        setUpUpComingRecycler(view)
        setUpObserver(view)



        return view
    }

    override fun onTripClick(tripId: String) {
        val intent = Intent(activity, TripPlanActivity::class.java)
        startActivity(intent)
    }

    private fun setUpUpComingRecycler(view: View){
        upComingTripListAdapter = UpComingTripListAdapter(this, mutableListOf())
        view.upcoming_recycler.layoutManager = LinearLayoutManager(activity,
            LinearLayoutManager.VERTICAL, false)
        view.upcoming_recycler.apply {
            setHasFixedSize(true)
            setItemViewCacheSize(20)
        }

        val snapHelperStart: SnapHelper = GravitySnapHelper(Gravity.TOP)
        snapHelperStart.attachToRecyclerView(view.upcoming_recycler)
        view.upcoming_recycler.isNestedScrollingEnabled = false
        view.upcoming_recycler.adapter = upComingTripListAdapter

    }

    private fun setUpObserver(view: View) {
        viewModel.getUpComingTripList().observe(viewLifecycleOwner, Observer {
            when(it) {
                is Resource.Loading -> {

                }
                is Resource.Success -> {
                    upComingTripListAdapter.setItems(it.data)
                }
                is Resource.Failure -> {
                    println(it.message)
                }
            }
        })
    }

}