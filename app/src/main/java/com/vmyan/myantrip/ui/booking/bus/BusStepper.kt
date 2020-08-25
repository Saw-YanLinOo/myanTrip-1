package com.vmyan.myantrip.ui.booking.bus

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.badoualy.stepperindicator.StepperIndicator
import com.vmyan.myantrip.R
import com.vmyan.myantrip.ui.NonSwipeableViewPager

class BusStepper : AppCompatActivity() , SelectSeatBus.OnStepOneListener ,BusDetails.OnBusDetailsListener,BusPayment.OnBusPaymentListener{
    private var mSectionsPagerAdapter: SectionsPagerAdapter? = null
    private lateinit var mViewPager: NonSwipeableViewPager
    private lateinit var stepperIndicator: StepperIndicator
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bus_stepper)
        mSectionsPagerAdapter = SectionsPagerAdapter(supportFragmentManager)

        // Set up the ViewPager with the sections adapter.
        mViewPager = findViewById(R.id.containerBus)
        mViewPager.setAdapter(mSectionsPagerAdapter)
        stepperIndicator = findViewById(R.id.stepperIndicatorBus)
        stepperIndicator.showLabels(true)
        stepperIndicator.setViewPager(mViewPager)
        // or keep last page as "end page"
        stepperIndicator.setViewPager(mViewPager, mViewPager.getAdapter()!!.count - 1)
    }
    inner class SectionsPagerAdapter(fm: FragmentManager?) : FragmentPagerAdapter(fm!!) {
        override fun getCount(): Int {
            // Show 3 total pages.
            return 4
        }

        override fun getPageTitle(position: Int): CharSequence? {
            when (position) {
                0 -> return "Select Seat"
                1 -> return "Details"
                2 -> return "Payment"
                3 -> return "Finish"
                /* 3 -> return "Fourth"
                 4 -> return "Finish"*/
            }
            return null
        }

        override fun getItem(position: Int): Fragment {
            when (position) {
                0 -> return SelectSeatBus.newInstance()
                1 -> return BusDetails.newInstance()
                2 -> return BusPayment.newInstance()
                3 -> return BusFinishPayment.newInstance()

            }
            return SelectSeatBus.newInstance()
        }
    }

    override fun onNextPressed(fragment: Fragment?) {
        if (fragment is SelectSeatBus) {
            mViewPager!!.setCurrentItem(1, true)
        } else if (fragment is BusDetails) {
            mViewPager!!.setCurrentItem(2, true)
        }else if (fragment is BusPayment){
            mViewPager.setCurrentItem(3,true)
        }else if (fragment is BusFinishPayment){
            mViewPager.setCurrentItem(4,true)
        }
    }
}