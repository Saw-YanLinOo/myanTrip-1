package com.vmyan.myantrip.ui.booking.bus.carRental

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.badoualy.stepperindicator.StepperIndicator
import com.vmyan.myantrip.R
import com.vmyan.myantrip.ui.NonSwipeableViewPager
import com.vmyan.myantrip.ui.booking.bus.flight.ConfirmFlight
import com.vmyan.myantrip.ui.booking.bus.flight.FinishFlightBooking
import com.vmyan.myantrip.ui.booking.bus.flight.FlightPayment

class CarRentalStepper : AppCompatActivity(),CarRentalsDetails.OnCarDetailsListener,CarRentalsPayment.OnCarRentalsPayment {
    private var mSectionsPagerAdapter: SectionsPagerAdapter? = null
    private lateinit var mViewPager: NonSwipeableViewPager
    private lateinit var stepperIndicator: StepperIndicator
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_car_rental_stepper)
        mSectionsPagerAdapter = SectionsPagerAdapter(supportFragmentManager)

        // Set up the ViewPager with the sections adapter.
        mViewPager = findViewById(R.id.containerCarRentails)
        mViewPager.setAdapter(mSectionsPagerAdapter)
        stepperIndicator = findViewById(R.id.stepperIndicatorCarRentals)
        stepperIndicator.showLabels(true)
        stepperIndicator.setViewPager(mViewPager)
        // or keep last page as "end page"
        stepperIndicator.setViewPager(mViewPager, mViewPager.getAdapter()!!.count - 1)
    }

    inner class SectionsPagerAdapter(fm: FragmentManager?) : FragmentPagerAdapter(fm!!) {
        override fun getCount(): Int {
            // Show 3 total pages.
            return 3
        }

        override fun getPageTitle(position: Int): CharSequence? {
            when (position) {
                0 -> return "Details"
                1 -> return "Payment"
                2 -> return "Finish"
                /* 3 -> return "Fourth"
                 4 -> return "Finish"*/
            }
            return null
        }

        override fun getItem(position: Int): Fragment {
            when (position) {
                0 -> return CarRentalsDetails.newInstance()
                1 -> return CarRentalsPayment.newInstance()
                2 -> return FinishCarRentails.newInstance()

            }
            return CarRentalsDetails.newInstance()
        }
    }

    override fun onNextPressed(fragment: Fragment?) {
        if (fragment is CarRentalsDetails) {
            mViewPager.setCurrentItem(1, true)
        } else if (fragment is CarRentalsPayment) {
            mViewPager.setCurrentItem(2, true)
        } else if (fragment is FinishCarRentails) {
            mViewPager.setCurrentItem(3, true)
            finish()
        }
    }

}