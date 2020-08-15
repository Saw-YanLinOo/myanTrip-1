package com.vmyan.myantrip.ui.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.vmyan.myantrip.ui.fragment.PastTripFragment
import com.vmyan.myantrip.ui.fragment.UpComingTripFragment

class TripViewPagerAdapter(fa: FragmentActivity) : FragmentStateAdapter(fa) {
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        when(position){
            0 -> return UpComingTripFragment()
            1 -> return PastTripFragment()
            else -> return UpComingTripFragment()
        }

    }

}