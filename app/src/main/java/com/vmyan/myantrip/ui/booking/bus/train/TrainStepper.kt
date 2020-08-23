package com.vmyan.myantrip.ui.booking.bus.train

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.badoualy.stepperindicator.StepperIndicator
import com.vmyan.myantrip.R
import com.vmyan.myantrip.ui.NonSwipeableViewPager

class TrainStepper : AppCompatActivity() {
    private var mSectionsPagerAdapter: SectionsPagerAdapter? = null
    private lateinit var mViewPager: NonSwipeableViewPager
    private lateinit var stepperIndicator: StepperIndicator
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_train_stepper)
        mSectionsPagerAdapter = SectionsPagerAdapter(supportFragmentManager)

        // Set up the ViewPager with the sections adapter.
        mViewPager = findViewById(R.id.containerTrain)
        mViewPager.setAdapter(mSectionsPagerAdapter)
        stepperIndicator = findViewById(R.id.stepperIndicatorTrain)
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
                2 -> return "Sucess"
                /* 3 -> return "Fourth"
                 4 -> return "Finish"*/
            }
            return null
        }

        override fun getItem(position: Int): Fragment {
            when (position) {
                0 -> return TrainDetails.newInstance()
                1 -> return TrainPayment.newInstance()
                2 -> return TrainFinish.newInstance()

            }
            return TrainDetails.newInstance()
        }
    }
}