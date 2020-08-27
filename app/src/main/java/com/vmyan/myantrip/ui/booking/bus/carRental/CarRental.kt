package com.vmyan.myantrip.ui.booking.bus.carRental

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.Window
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
import com.vmyan.myantrip.R
import com.vmyan.myantrip.data.booking.carRental.CarListRepositoryImpl
import com.vmyan.myantrip.data.booking.carRental.hotel.HotelListRepositoryImpl
import com.vmyan.myantrip.model.carRental.CarRentalRecentItem
import com.vmyan.myantrip.model.hotel.HotelRecentItem
import com.vmyan.myantrip.ui.adapter.carRental.CarPromoAdapter
import com.vmyan.myantrip.ui.adapter.carRental.CarRecentAdapter
import com.vmyan.myantrip.ui.adapter.hotel.HotelRecentAdapter
import com.vmyan.myantrip.ui.adapter.promoImageAdapter.PromoSliderImageAdapter
import com.vmyan.myantrip.ui.booking.bus.PostActivityContract
import com.vmyan.myantrip.ui.booking.bus.hotel.HotelBooking
import com.vmyan.myantrip.ui.viewmodel.carRental.CarListVM
import com.vmyan.myantrip.ui.viewmodel.carRental.CarListVMFactory
import com.vmyan.myantrip.ui.viewmodel.carRental.CarRecentViewModel
import com.vmyan.myantrip.ui.viewmodel.hotel.HotelListViewModel
import com.vmyan.myantrip.ui.viewmodel.hotel.RecentViewModel
import com.vmyan.myantrip.utils.Resource
import kotlinx.android.synthetic.main.activity_car_rental.*
import kotlinx.android.synthetic.main.activity_hotel_booking.*
import kotlinx.android.synthetic.main.show_empty_message.*
import java.util.*
import kotlin.time.ExperimentalTime

class CarRental : AppCompatActivity() {
    private var dPickUpCar : String=""
    private var dDropOffCar : String=""
    private var location : String=""
    private var cityImagge : String=""
    private var startDate: Timestamp? = null
    private var endDate: Timestamp? = null
    private var noOfCarPeople :Int=0
    private lateinit var recentViewModel: CarRecentViewModel
    private lateinit var sliderImageAdapter: PromoSliderImageAdapter
    private val viewModel by lazy {
        ViewModelProviders.of(
            this,
            CarListVMFactory(
                CarListRepositoryImpl()
            )
        ).get(
            CarListVM::class.java
        )
    }
    @ExperimentalTime
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_car_rental)
        pickPlace()
        setUpAdapter()
        setCarPromoImage()
        showImageSlider()
        carRentalBackHome.setOnClickListener {
            this.finish()
        }

        card_CarRentailSearch.setOnClickListener {
            noOfCarPeople= carRental_PeopleCount.rating.toInt()
            if (location == "" && dPickUpCar == "" && dDropOffCar == "" && noOfCarPeople == 1 ) {
                val dialog = Dialog(this)
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
                dialog.setCancelable(false)
                dialog.setContentView(R.layout.show_empty_message)
                dialog.btnOk.setOnClickListener {
                    dialog.dismiss()
                }
                dialog.show()
            }else {
                val intent = Intent(this, CarRentalsListView::class.java)
                intent.putExtra("CarLocation",location)
                intent.putExtra("CarStart",dPickUpCar)
                intent.putExtra("CarEnd",dDropOffCar)
                intent.putExtra("CarNoOfPeople",noOfCarPeople.toString())
                startActivity(intent)
                recentViewModel.insert(CarRentalRecentItem(cityImagge,
                    location,
                    dPickUpCar,
                    dDropOffCar,
                    noOfCarPeople.toString()))
            }
        }
        clearAllCarRecentValue.setOnClickListener {
            recentViewModel.clearAll()
        }
        rl_PickUp_CarRental.setOnClickListener {
            datePicker("start")
        }
        rl_DropOff_CarRentals.setOnClickListener {
            datePicker("end")
        }
    }
    @SuppressLint("ShowToast")
    private fun setCarPromoImage() {

        viewModel.fetchCarPromoImages.observe(this,  {
            when (it) {
                is Resource.Loading -> {
                    carRental_Promo_Placeholder.startShimmer()
                    carRental_Promo_Placeholder.visibility = View.VISIBLE
                    carRental_Promo_Images.visibility = View.GONE
                }
                is Resource.Success -> {
                    carRental_Promo_Placeholder.startShimmer()
                    carRental_Promo_Placeholder.visibility = View.GONE
                    carRental_Promo_Images.visibility = View.VISIBLE
                    sliderImageAdapter.setItem(it.data[0].carPromoImages)

                }
                is Resource.Failure -> {
                    carRental_Promo_Placeholder.startShimmer()
                    carRental_Promo_Placeholder.visibility = View.GONE
                    carRental_Promo_Images.visibility = View.GONE
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
        carRental_Promo_Images.setSliderAdapter(sliderImageAdapter)        //set indicator animation by using SliderLayout.IndicatorAnimations. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!
        carRental_Promo_Images.setIndicatorAnimation(IndicatorAnimationType.WORM)
        carRental_Promo_Images.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION)
        carRental_Promo_Images.autoCycleDirection = SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH
        carRental_Promo_Images.indicatorSelectedColor = Color.WHITE
        carRental_Promo_Images.indicatorUnselectedColor = Color.GRAY
        carRental_Promo_Images.scrollTimeInSec = 3
        carRental_Promo_Images.isAutoCycle = true
        carRental_Promo_Images.startAutoCycle()

    }

    private fun pickPlace() {
        rl_carRentalPickLocation.setOnClickListener {
            openPostActivityCustom.launch(1)
            location=txtCarRentalLocation.text.toString()

        }
    }
    private val openPostActivityCustom =
        registerForActivityResult(PostActivityContract()) { result ->
            if (result != null) {
                txtCarRentalLocation.text = result[0]
                location = result[0]
                cityImagge=result[1]
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
        constraintsBuilderRange.setValidator(HotelBooking.RangeValidator(minDate, maxDate))
        return constraintsBuilderRange
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
                    txtPickUpCarRentals.text = picker.headerText
                    dPickUpCar=picker.headerText
                    startDate = Timestamp(Date(it))
                }
                "end" -> {
                    txt_DropOffCarRentals.text = picker.headerText
                    dDropOffCar=picker.headerText
                    endDate = Timestamp(Date(it))
                }
            }
        }
    }
    private fun setUpAdapter() {
        val recyclerView = findViewById<RecyclerView>(R.id.rv_CarRecentItem)
        val adapter =CarRecentAdapter (this)

        ViewCompat.setNestedScrollingEnabled(rv_CarRecentItem, false)


        recyclerView.adapter = adapter
        recyclerView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        // Get a new or existing ViewModel from the ViewModelProvider.
        recentViewModel = ViewModelProvider(this).get(CarRecentViewModel::class.java)

        // Add an observer on the LiveData returned by getAlphabetizedWords.
        // The onChanged() method fires when the observed data changes and the activity is
        // in the foreground.

        recentViewModel.allItems.observe(this,  { words ->
            // Upd
            // ate the cached copy of the words in the adapter.
            words?.let { adapter.setWords(it) }
        })

    }
}