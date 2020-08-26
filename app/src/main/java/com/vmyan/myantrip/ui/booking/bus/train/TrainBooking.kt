package com.vmyan.myantrip.ui.booking.bus.train

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.Toast
import androidx.core.view.ViewCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SnapHelper
import com.bumptech.glide.Glide
import com.github.rubensousa.gravitysnaphelper.GravitySnapHelper
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.firebase.Timestamp
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType
import com.smarteist.autoimageslider.SliderAnimations
import com.smarteist.autoimageslider.SliderView
import com.vmyan.myantrip.R
import com.vmyan.myantrip.data.booking.carRental.CarListRepositoryImpl
import com.vmyan.myantrip.data.booking.carRental.train.TrainRepositoryImpl
import com.vmyan.myantrip.model.flight.FlightRecentItem
import com.vmyan.myantrip.model.train.TrainRecentItem
import com.vmyan.myantrip.ui.adapter.flight.FlightRecentAdapter
import com.vmyan.myantrip.ui.adapter.promoImageAdapter.PromoSliderImageAdapter
import com.vmyan.myantrip.ui.adapter.train.TrainRecentAdapter
import com.vmyan.myantrip.ui.booking.bus.CityFromPostActivityContract
import com.vmyan.myantrip.ui.booking.bus.CityToPostActivityContract
import com.vmyan.myantrip.ui.booking.bus.PostActivityContract
import com.vmyan.myantrip.ui.booking.bus.hotel.HotelBooking
import com.vmyan.myantrip.ui.bs.SelectTrainPassenger
import com.vmyan.myantrip.ui.viewmodel.carRental.CarListVM
import com.vmyan.myantrip.ui.viewmodel.carRental.CarListVMFactory
import com.vmyan.myantrip.ui.viewmodel.flight.FlightRecentViewModel
import com.vmyan.myantrip.ui.viewmodel.hotel.RecentViewModel
import com.vmyan.myantrip.ui.viewmodel.train.TrainRecentViewModel
import com.vmyan.myantrip.ui.viewmodel.train.TrainVM
import com.vmyan.myantrip.ui.viewmodel.train.TrainVMFactory
import com.vmyan.myantrip.utils.Resource
import kotlinx.android.synthetic.main.activity_bus_booking.*
import kotlinx.android.synthetic.main.activity_car_rental.*
import kotlinx.android.synthetic.main.activity_flight_booking.*
import kotlinx.android.synthetic.main.activity_train_booking.*
import java.util.*
import kotlin.time.ExperimentalTime

class TrainBooking : AppCompatActivity() {
    private var dTrainDepartDate : String=""
    private var trainFrom : String=""
    private var trainFromImage: String=""
    private var trainTo : String=""
    private var trainToImage : String=""
    private var trainNoOfPeople : Int=0
    private var startDate: Timestamp? = null
    private lateinit var sliderImageAdapter: PromoSliderImageAdapter
    private val viewModel by lazy {
        ViewModelProviders.of(
            this,
            TrainVMFactory(
                TrainRepositoryImpl()
            )
        ).get(
            TrainVM::class.java
        )
    }
    private lateinit var trainRecentViewModel: TrainRecentViewModel
    @ExperimentalTime
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_train_booking)

        clearAllTrainRecentValue.setOnClickListener {
            trainRecentViewModel.clearAll()
        }
        rl_trainDepartDate.setOnClickListener {
            datePicker("start")
        }
        pickPlace()
        setTrainPromoImage()
        showImageSlider()
        setTrainRecentList()

        card_TrainSearch.setOnClickListener {
            trainNoOfPeople=train_PeopleCount.rating.toInt()
            val intent =Intent(this, TrainViewList::class.java)
            intent.putExtra("TrainImgFrom",trainFromImage)
            intent.putExtra("TrainTxtFrom",trainFrom)
            intent.putExtra("TrainImgTo",trainToImage)
            intent.putExtra("TrainTxtTo",trainTo)
            intent.putExtra("TrainDepartDate",dTrainDepartDate)

            startActivity(intent)
            trainRecentViewModel.insert(TrainRecentItem(trainFrom,trainTo,dTrainDepartDate,trainNoOfPeople.toString()))
        }
    }
    private fun pickPlace() {
        rl_TrainFrom.setOnClickListener {
            cityFromOpenPostActivityCustom.launch(1)
        }
        rl_TrainTo.setOnClickListener {
            cityToOpenPostActivityCustom.launch(1)
        }
    }
    private val cityFromOpenPostActivityCustom =
        registerForActivityResult(CityFromPostActivityContract()) { result ->
            if (result != null) {
                txtTrainFrom.text = result[0]
                trainFrom = result[0]
                trainFromImage=result[1]
            }
        }
    private val cityToOpenPostActivityCustom =
        registerForActivityResult(CityToPostActivityContract()) { result ->
            if (result != null) {
                if (result != null) {
                    txtTrainTo.text = result[0]
                    trainTo = result[0]
                    trainToImage=result[1]
                }
            }
        }

    private fun setTrainRecentList() {
        val recyclerView = findViewById<RecyclerView>(R.id.rv_TrainRecentItem)
        val adapter = TrainRecentAdapter(this)
        ViewCompat.setNestedScrollingEnabled(rv_TrainRecentItem, false)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        // Get a new or existing ViewModel from the ViewModelProvider.
        trainRecentViewModel = ViewModelProvider(this).get(TrainRecentViewModel::class.java)

        // Add an observer on the LiveData returned by getAlphabetizedWords.
        // The onChanged() method fires when the observed data changes and the activity is
        // in the foreground.
        trainRecentViewModel.allItems.observe(this) { words ->
            words.let { adapter.setWords(it) }
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
                    trainDepartDate.text = picker.headerText
                    dTrainDepartDate=picker.headerText
                    startDate = Timestamp(Date(it))
                }
            }
        }
    }
    @SuppressLint("ShowToast")
    private fun setTrainPromoImage() {

        viewModel.fetchTrainPromoImages.observe(this,  {
            when (it) {
                is Resource.Loading -> {
                    train_Promo_Placeholder.startShimmer()
                    train_Promo_Placeholder.visibility = View.VISIBLE
                    train_Promo_Images.visibility = View.GONE
                }
                is Resource.Success -> {
                    train_Promo_Placeholder.startShimmer()
                    train_Promo_Placeholder.visibility = View.GONE
                    train_Promo_Images.visibility = View.VISIBLE
                    sliderImageAdapter.setItem(it.data[0].trainImagePro)

                }
                is Resource.Failure -> {
                    train_Promo_Placeholder.startShimmer()
                    train_Promo_Placeholder.visibility = View.GONE
                    train_Promo_Images.visibility = View.GONE
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
        train_Promo_Images.setSliderAdapter(sliderImageAdapter)        //set indicator animation by using SliderLayout.IndicatorAnimations. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!
        train_Promo_Images.setIndicatorAnimation(IndicatorAnimationType.WORM)
        train_Promo_Images.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION)
        train_Promo_Images.autoCycleDirection = SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH
        train_Promo_Images.indicatorSelectedColor = Color.WHITE
        train_Promo_Images.indicatorUnselectedColor = Color.GRAY
        train_Promo_Images.scrollTimeInSec = 3
        train_Promo_Images.isAutoCycle = true
        train_Promo_Images.startAutoCycle()

    }
}