package com.vmyan.myantrip.ui.booking.bus.hotel

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.view.Gravity
import android.view.View
import android.widget.Toast
import androidx.core.view.ViewCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SnapHelper
import com.github.rubensousa.gravitysnaphelper.GravitySnapHelper
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.firebase.Timestamp
import com.minbanyar.testbooking.viewModel.hotel.HotelListVMFactory
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType
import com.smarteist.autoimageslider.SliderAnimations
import com.smarteist.autoimageslider.SliderView
import com.vmyan.myantrip.ui.interfaceImpl.PickDate
import com.vmyan.myantrip.ui.interfaceImpl.RoomGuest
import com.vmyan.myantrip.ui.bs.SelectRoomGuests
import com.vmyan.myantrip.R
import com.vmyan.myantrip.data.booking.carRental.hotel.HotelListRepositoryImpl
import com.vmyan.myantrip.model.hotel.HotelRecentItem
import com.vmyan.myantrip.ui.booking.bus.PostActivityContract
import com.vmyan.myantrip.ui.adapter.hotel.HotelRecentAdapter
import com.vmyan.myantrip.ui.adapter.promoImageAdapter.PromoSliderImageAdapter
import com.vmyan.myantrip.ui.viewmodel.hotel.HotelListViewModel
import com.vmyan.myantrip.ui.viewmodel.hotel.RecentViewModel
import com.vmyan.myantrip.utils.Resource
import kotlinx.android.synthetic.main.activity_hotel_booking.*
import java.util.*
import kotlin.time.ExperimentalTime

class HotelBooking : AppCompatActivity(), PickDate, RoomGuest {
    private val TAG = "NumberPicker"
    private var location : String=""
    private var cityImagge : String=""
    private var dCheckIn : String=""
    private var dCheckOut : String=""
    private var noOfR : String=""
    private var noOfG : String=""

    private var startDate: Timestamp? = null
    private var endDate: Timestamp? = null
    private lateinit var sliderImageAdapter: PromoSliderImageAdapter

    private lateinit var recentViewModel: RecentViewModel
    private val viewModel by lazy {
        ViewModelProviders.of(
            this,
            HotelListVMFactory(
                HotelListRepositoryImpl()
            )
        ).get(
            HotelListViewModel::class.java
        )
    }
    @ExperimentalTime
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hotel_booking)
        val hid = intent.getStringExtra("id")

        backHome.setOnClickListener {
            this.finish()
        }
        // val stayPlace :String =Hawk.get("StayingPlace")
        setUpAdapter()
        pickPlace()
        pickRG()
        showImageSlider()
        setHotelPromoImage()
        card_searchHotel.setOnClickListener {

            /*     if (location ==""  && dCheckIn =="" && dCheckOut ==""&& noOfG =="" && noOfR =="" ){
                         val dialog =Dialog(this)
                         dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
                         dialog.setCancelable(false)
                         dialog.setContentView(R.layout.show_empty_message)

                         dialog.btnOk.setOnClickListener {
                             dialog.dismiss()
                         }
                     dialog.show()



                 }
                 else{*/
            val intent = Intent(applicationContext,ShowHotelView::class.java)
            intent.putExtra("id",hid)
            intent.putExtra("LocationName",location)
            intent.putExtra("DateCheckIn",dCheckIn)
            intent.putExtra("DatecheckOut",dCheckOut)
            intent.putExtra("NumberOfRoom",noOfR)
            intent.putExtra("NumberOfGuests",noOfG)
            startActivity(intent)
            recentViewModel.insert(HotelRecentItem(cityImagge,location,dCheckIn,dCheckOut,noOfG,noOfR))


        }
        clearAllRecentValue.setOnClickListener {
            recentViewModel.clearAll()
        }

        rl_PickDateCheckInHotel.setOnClickListener {
            datePicker("start")
        }
        rl_PickDateCheckOutHotel.setOnClickListener {
            datePicker("end")
        }
    }
    @SuppressLint("ShowToast")
    private fun setHotelPromoImage() {

        viewModel.fetchHotelPromoImages.observe(this,  {
            when (it) {
                is Resource.Loading -> {
                  hotel_Promo_Placeholder.startShimmer()
                    hotel_Promo_Placeholder.visibility = View.VISIBLE
                    hotel_Promo_Images.visibility = View.GONE
                }
                is Resource.Success -> {
                    hotel_Promo_Placeholder.startShimmer()
                    hotel_Promo_Placeholder.visibility = View.GONE
                    hotel_Promo_Images.visibility = View.VISIBLE
                    sliderImageAdapter.setItem(it.data[0].hotelImagePro)

                }
                is Resource.Failure -> {
                    hotel_Promo_Placeholder.startShimmer()
                    hotel_Promo_Placeholder.visibility = View.GONE
                    hotel_Promo_Images.visibility = View.GONE
                    println(it.message)
                    Toast.makeText(
                        this,
                        "An error is ocurred:${it.message}",
                        Toast.LENGTH_SHORT
                    )
                }
            }
        })

    }


    private fun showImageSlider(){
        sliderImageAdapter = PromoSliderImageAdapter()
        hotel_Promo_Images.setSliderAdapter(sliderImageAdapter)        //set indicator animation by using SliderLayout.IndicatorAnimations. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!
       hotel_Promo_Images.setIndicatorAnimation(IndicatorAnimationType.WORM)
         hotel_Promo_Images.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION)
         hotel_Promo_Images.autoCycleDirection = SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH
        hotel_Promo_Images.indicatorSelectedColor = Color.WHITE
        hotel_Promo_Images.indicatorUnselectedColor = Color.GRAY
        hotel_Promo_Images.scrollTimeInSec = 3
        hotel_Promo_Images.isAutoCycle = true
        hotel_Promo_Images.startAutoCycle()

    }
    private fun pickPlace() {
        rl_PickPlace.setOnClickListener {
            openPostActivityCustom.launch(1)
            location=appComTxtPickLoacation.text.toString()

        }
    }
    private val openPostActivityCustom =
        registerForActivityResult(PostActivityContract()) { result ->
            if (result != null) {
                appComTxtPickLoacation.text = result[0]
                location = result[0]
                cityImagge=result[1]
            }
        }

    @SuppressLint("ResourceType")
    private fun pickRG(){
        rl_PickRoomGuests.setOnClickListener {
            val bottomSheet= SelectRoomGuests()
            bottomSheet.show(supportFragmentManager,bottomSheet.tag)
        }
    }



    @ExperimentalTime
    private fun datePicker(status: String){
        val builder = MaterialDatePicker.Builder.datePicker()
        builder.setCalendarConstraints(limitRange()!!.build())
        val picker = builder.build()
        picker.show(supportFragmentManager, picker.toString())
        picker.addOnPositiveButtonClickListener {
            when(status){
                "start" -> {
                    txt_CheckInHotel.text = picker.headerText
                    dCheckIn=picker.headerText
                    startDate = Timestamp(Date(it))
                }
                "end" -> {
                    appComTxt_CheckoutHotel.text = picker.headerText
                    dCheckOut=picker.headerText
                    endDate = Timestamp(Date(it))
                }
            }
        }
    }
    private fun limitRange(): CalendarConstraints.Builder? {
        val constraintsBuilderRange = CalendarConstraints.Builder()
        val calendarStart = Calendar.getInstance()
        val calendarEnd = Calendar.getInstance()
        val year = 2022
//        val startMonth = 2
//        val startDate = 15
        val endMonth = 12
        val endDate = 31
        //calendarStart[year, startMonth - 1] = startDate - 1
        calendarEnd[year, endMonth - 1] = endDate
        val minDate = calendarStart.timeInMillis
        val maxDate = calendarEnd.timeInMillis
        constraintsBuilderRange.setStart(minDate)
        constraintsBuilderRange.setEnd(maxDate)
        constraintsBuilderRange.setValidator(RangeValidator(minDate, maxDate))
        return constraintsBuilderRange
    }

    private fun setUpAdapter() {
        val recyclerView = findViewById<RecyclerView>(R.id.rv_hotelRecentItem)
        val snapHelperStart: SnapHelper = GravitySnapHelper(Gravity.START)
        snapHelperStart.attachToRecyclerView(rv_hotelRecentItem)
        val adapter = HotelRecentAdapter(this)

        ViewCompat.setNestedScrollingEnabled(rv_hotelRecentItem, false)


        recyclerView.adapter = adapter
        recyclerView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        // Get a new or existing ViewModel from the ViewModelProvider.
        recentViewModel = ViewModelProvider(this).get(RecentViewModel::class.java)

        // Add an observer on the LiveData returned by getAlphabetizedWords.
        // The onChanged() method fires when the observed data changes and the activity is
        // in the foreground.

        recentViewModel.allItems.observe(this,  { words ->
            // Upd
            // ate the cached copy of the words in the adapter.
            words?.let { adapter.setWords(it) }
        })

    }



    override fun dataCheckIn(dateCheckInValue: String) {
        txt_CheckInHotel.text = dateCheckInValue
        dCheckIn=dateCheckInValue
    }

    override fun dateCheckOut(dateCheckOutValue: String) {
        appComTxt_CheckoutHotel.text=dateCheckOutValue
        dCheckOut=dateCheckOutValue
    }

    override fun room(number: Int) {
        appComNoOFRoom.text= number.toString()
        noOfR=number.toString()

    }

    override fun guest(number: Int) {
        appComNoOFGuest.text=number.toString()
        noOfG=number.toString()
    }

    internal class RangeValidator : CalendarConstraints.DateValidator {
        var minDate: Long
        var maxDate: Long

        constructor(minDate: Long, maxDate: Long) {
            this.minDate = minDate
            this.maxDate = maxDate
        }

        constructor(parcel: Parcel) {
            minDate = parcel.readLong()
            maxDate = parcel.readLong()
        }

        override fun isValid(date: Long): Boolean {
            return !(minDate > date || maxDate < date)
        }

        override fun describeContents(): Int {
            return 0
        }

        override fun writeToParcel(dest: Parcel, flags: Int) {
            dest.writeLong(minDate)
            dest.writeLong(maxDate)
        }

        companion object {
            val CREATOR: Parcelable.Creator<RangeValidator?> =
                object : Parcelable.Creator<RangeValidator?> {
                    override fun createFromParcel(parcel: Parcel): RangeValidator? {
                        return RangeValidator(parcel)
                    }

                    override fun newArray(size: Int): Array<RangeValidator?> {
                        return arrayOfNulls(size)
                    }
                }
        }
    }
}