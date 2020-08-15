package com.vmyan.myantrip.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.vmyan.myantrip.R
import com.vmyan.myantrip.ui.adapter.TripViewPagerAdapter
import kotlinx.android.synthetic.main.fragment_plan.*
import kotlinx.android.synthetic.main.fragment_plan.view.*

class PlanFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_plan, container, false)

        view.viewPager2.adapter = TripViewPagerAdapter(requireActivity())
        TabLayoutMediator(view.tabLayout, view.viewPager2,
            TabLayoutMediator.TabConfigurationStrategy { tab, position ->
                when(position){
                    0 -> tab.text = "Upcoming"
                    1 -> tab.text = "Past"
                }
            }).attach()

        return view
    }

}