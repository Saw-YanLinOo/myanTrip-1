package com.vmyan.myantrip.ui.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.vmyan.myantrip.ui.fragment.ExpireTicket
import com.vmyan.myantrip.ui.fragment.PreBooking

class ExpierPreViewPagerAdapter(fa: FragmentActivity) : FragmentStateAdapter(fa) {
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        when (position) {
            0 -> return PreBooking()
            1 -> return ExpireTicket()
            else -> return PreBooking()
        }

    }
}