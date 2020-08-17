package com.vmyan.myantrip.ui.fragment

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
import com.vmyan.myantrip.ui.adapter.SearchPlaceListAdapter
import com.vmyan.myantrip.ui.adapter.UpComingTripListAdapter
import com.vmyan.myantrip.ui.viewmodel.*
import com.vmyan.myantrip.utils.Resource
import kotlinx.android.synthetic.main.activity_search_place.*
import kotlinx.android.synthetic.main.fragment_past_trip.view.*
import kotlinx.android.synthetic.main.fragment_up_coming_trip.view.*
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.android.x.closestDI
import org.kodein.di.instance

class PastTripFragment : Fragment(), UpComingTripListAdapter.ItemClickListener, DIAware {

    override val di: DI by closestDI()

    private val viewModelFactory : PastTripVMFactory by instance()

    private lateinit var viewModel: PastTripViewModel
    private lateinit var upComingTripListAdapter: UpComingTripListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this, viewModelFactory).get(PastTripViewModel::class.java)
        val view = inflater.inflate(R.layout.fragment_past_trip, container, false)

        setUpUpComingRecycler(view)
        setUpObserver(view)



        return view
    }

    override fun onTripClick(tripId: String) {
        println(tripId)
    }

    private fun setUpUpComingRecycler(view: View){
        upComingTripListAdapter = UpComingTripListAdapter(this, mutableListOf())
        view.past_recycler.layoutManager = LinearLayoutManager(activity,
            LinearLayoutManager.VERTICAL, false)
        view.past_recycler.apply {
            setHasFixedSize(true)
            setItemViewCacheSize(20)
        }

        val snapHelperStart: SnapHelper = GravitySnapHelper(Gravity.TOP)
        snapHelperStart.attachToRecyclerView(view.past_recycler)
        view.past_recycler.isNestedScrollingEnabled = false
        view.past_recycler.adapter = upComingTripListAdapter

    }

    private fun setUpObserver(view: View) {
        viewModel.getPastTripList().observe(viewLifecycleOwner, Observer {
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