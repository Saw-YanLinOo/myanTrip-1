package com.vmyan.myantrip.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.SnapHelper
import com.github.rubensousa.gravitysnaphelper.GravitySnapHelper
import com.google.firebase.auth.FirebaseAuth
import com.vmyan.myantrip.R
import com.vmyan.myantrip.ui.TripPlanActivity
import com.vmyan.myantrip.ui.adapter.UpComingTripListAdapter
import com.vmyan.myantrip.ui.viewmodel.UpComingTripViewModel
import com.vmyan.myantrip.utils.NoAccountDialog
import com.vmyan.myantrip.utils.Resource
import kotlinx.android.synthetic.main.fragment_up_coming_trip.view.*
import org.koin.android.ext.android.inject

class UpComingTripFragment : Fragment(), UpComingTripListAdapter.ItemClickListener{

    private val viewModel: UpComingTripViewModel by inject()
    private lateinit var upComingTripListAdapter: UpComingTripListAdapter
    private val auth = FirebaseAuth.getInstance()

    override fun onResume() {
        super.onResume()
        setUpObserver(requireView())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_up_coming_trip, container, false)


        val userid = auth.currentUser?.uid.toString().trim()

        if (userid == "null"){
            NoAccountDialog(requireContext()).noAccountDialog()
        }else{
            setUpUpComingRecycler(view)
            setUpObserver(view)
        }

        return view
    }

    override fun onTripClick(tripId: String) {
        val intent = Intent(activity, TripPlanActivity::class.java)
        intent.putExtra("tripId",tripId)
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