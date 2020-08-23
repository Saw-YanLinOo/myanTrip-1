package com.vmyan.myantrip.ui.booking.bus.hotel

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.badoualy.stepperindicator.StepperIndicator
import com.vmyan.myantrip.R
import com.vmyan.myantrip.ui.NonSwipeableViewPager


class HotelStepper : AppCompatActivity(), HotelRoomList.OnStepOneListener,
    HotelConfirmRoom.OnStepPaymentListener,
    HotelPayment.OnStepOneListener {
    private var mSectionsPagerAdapter: SectionsPagerAdapter? = null
    private lateinit var mViewPager: NonSwipeableViewPager
    private lateinit var stepperIndicator: StepperIndicator
    private lateinit var id :String
    private lateinit var cid :String

    private lateinit var roomId :String
    private lateinit var hotelCheckIn :String
    private lateinit var hotelCheckOut :String
    private lateinit var hotelNoOfRoom : String
    private lateinit var hotelNoOfGuests : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hotel_stepper)
        mSectionsPagerAdapter = SectionsPagerAdapter(supportFragmentManager)

        id = intent.getStringExtra("id").toString()
        cid = intent.getStringExtra("cid").toString()
        hotelCheckIn=intent.getStringExtra("TxtHotelCheckIn").toString()
        hotelCheckOut=intent.getStringExtra("TxtHotelCheckOut").toString()
        hotelNoOfRoom=intent.getStringExtra("SelectedRoomNo").toString()
        hotelNoOfGuests=intent.getStringExtra("SelectedGuestsNo").toString()

        // Set up the ViewPager with the sections adapter.
        mViewPager = findViewById(R.id.containerHotel)
        mViewPager.setAdapter(mSectionsPagerAdapter)
        stepperIndicator = findViewById(R.id.stepperIndicatorHotel)
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
                0 -> return "Select Room"
                1 -> return "Confirm"
                2 -> return "Payment"
                3 -> return "Success"
                /* 3 -> return "Fourth"
                 4 -> return "Finish"*/
            }
            return null
        }

        override fun getItem(position: Int): Fragment {

            when (position) {
                0 -> return HotelRoomList.newInstance(id,cid,hotelCheckIn,hotelCheckOut)

                1 -> return HotelConfirmRoom.newInstance(
                    id,
                    cid,
                    hotelCheckIn,
                    hotelCheckOut,
                    hotelNoOfRoom,
                    hotelNoOfGuests
                )
                2 -> return HotelPayment.newInstance()
                3 -> return FinishPayment.newInstance()

            }
            return HotelRoomList.newInstance(id,cid,hotelCheckIn,hotelCheckOut)
        }
    }


    override fun onNextPressed(fragment: Fragment?) {

        if (fragment is HotelRoomList) {
            mViewPager.setCurrentItem(1, true)
        } else if (fragment is HotelConfirmRoom) {
            mViewPager.setCurrentItem(2, true)
        }else if (fragment is HotelPayment){
            mViewPager.setCurrentItem(3,true)

        }else if(fragment is FinishPayment){
            mViewPager.setCurrentItem(4,true)
            finish()
        }
    }
}