package com.vmyan.myantrip.ui.booking.bus.flight

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SnapHelper
import com.bumptech.glide.Glide
import com.github.rubensousa.gravitysnaphelper.GravitySnapHelper
import com.google.android.material.datepicker.MaterialDatePicker
import com.vmyan.myantrip.R
import com.vmyan.myantrip.model.flight.FlightRecentItem
import com.vmyan.myantrip.ui.PostActivityContract
import com.vmyan.myantrip.ui.adapter.flight.FlightRecentAdapter
import com.vmyan.myantrip.ui.viewmodel.flight.FlightRecentViewModel
import kotlinx.android.synthetic.main.activity_flight_booking.*
import nl.dionsegijn.steppertouch.OnStepCallback
import java.security.Timestamp
import kotlin.time.ExperimentalTime


class FlightBooking : AppCompatActivity(), View.OnClickListener {
    private var flightFrom : String=""
    private var flightfromImagge : String=""
    private var flightTo : String=""
    private var flightToImagge : String=""
    private var startDate: Timestamp? = null
    private  var departDepart : String=""
    private  var fligthDepartDate :String=""
    private  var fAultsCount :String=""
    private  var fChildCount :String=""
    private  var fInfantCount :String=""
    private  var flightCLass :String=""
    private lateinit var flightRecentViewModel: FlightRecentViewModel
    @ExperimentalTime
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_flight_booking)
        card_Economy.setOnClickListener(this)
        card_Business.setOnClickListener(this)
        card_First.setOnClickListener(this)
        setUpAdapter()
        pickAdultsCount()
        pickChildCount()
        pickInfantCount()

        card_FlightSearch.setOnClickListener{
            val intent = Intent (this, FlightListView::class.java)
            intent.putExtra("AdultsCount",fAultsCount)
            intent.putExtra("ChildCount",fChildCount)
            intent.putExtra("InfantCount",fInfantCount)
            intent.putExtra("ImgFlightFrom",flightfromImagge)
            intent.putExtra("ImgFlightTo",flightToImagge)
            intent.putExtra("FlightDepartDate",departDepart)
            intent.putExtra("FlightFromTxt",flightFrom)
            intent.putExtra("FlightToTxt",flightTo)
          //intent.putExtra("FlightClass",flightCLass )
            startActivity(intent)
            flightRecentViewModel.insert(FlightRecentItem(flightFrom,flightTo,departDepart,fAultsCount,fChildCount,fInfantCount))
        }
        pickPlace()
        rl_PickDateFlightDepart.setOnClickListener {
            datePicker("start")

        }

    }
    private fun pickAdultsCount(){
        stAdultsValue.minValue = 0
        stAdultsValue.maxValue = 10
        stAdultsValue.sideTapEnabled = true
        stAdultsValue.addStepCallback(object : OnStepCallback {
            override fun onStep(value: Int, positive: Boolean) {
                fAultsCount=value.toString()
                Toast.makeText(applicationContext, value.toString(), Toast.LENGTH_SHORT).show()
            }
        })
    }
    private fun pickChildCount(){
        stChildValue.minValue = 0
        stChildValue.maxValue = 10
        stChildValue.sideTapEnabled = true
        stChildValue.addStepCallback(object : OnStepCallback {
            override fun onStep(value: Int, positive: Boolean) {
                fChildCount=value.toString()
                Toast.makeText(applicationContext, value.toString(), Toast.LENGTH_SHORT).show()
            }
        })

    }
    private fun pickInfantCount(){
        stInfantValue.minValue = 0
        stInfantValue.maxValue = 10
        stInfantValue.sideTapEnabled = true
        stInfantValue.addStepCallback(object : OnStepCallback {
            override fun onStep(value: Int, positive: Boolean) {
                fInfantCount=value.toString()
                Toast.makeText(applicationContext, value.toString(), Toast.LENGTH_SHORT).show()
            }
        })

    }
    private fun pickPlace() {
        llFlightFrom.setOnClickListener {
            openPostActivityCustom.launch(1)
            //lightFrom=txt_FligtFrom.text.toString()

        }
        llFlightTo.setOnClickListener {
            flightToClicked.launch(1)
        }
    }
    private val openPostActivityCustom =
        registerForActivityResult(PostActivityContract()) { result ->
            if (result != null) {
                txt_FligtFrom.text = result[0]
                Glide.with(this).load(result[1]).into(imgFlightFrom)
                flightFrom = result[0]
                flightfromImagge=result[1]
            }
        }
    private val flightToClicked =
        registerForActivityResult(PostActivityContract()) { result ->
            if (result != null) {
                txt_FligtTo.text = result[0]
                Glide.with(this).load(result[1]).into(imgFlightTo)
                flightTo = result[0]
                flightToImagge=result[1]
            }
        }

    @SuppressLint("SimpleDateFormat")
    @RequiresApi(Build.VERSION_CODES.O)
    @ExperimentalTime
    private fun datePicker(status: String){
        val builder = MaterialDatePicker.Builder.datePicker()
        val picker = builder.build()
        picker.show(supportFragmentManager, picker.toString())
        picker.addOnPositiveButtonClickListener {
            when(status){
                "start" -> {
                    appComtxt_FlightDepart.text = picker.headerText
                    departDepart=picker.headerText
                    /*startDate = Timestamp(Date(it))*/
                }

            }
        }
    }
    private fun setUpAdapter() {
        val recyclerView = findViewById<RecyclerView>(R.id.rv_FlightRecentItem)
        val snapHelperStart: SnapHelper = GravitySnapHelper(Gravity.START)
        snapHelperStart.attachToRecyclerView(rv_FlightRecentItem)
        val adapter = FlightRecentAdapter(this)

        ViewCompat.setNestedScrollingEnabled(rv_FlightRecentItem, false)


        recyclerView.adapter = adapter
        recyclerView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        // Get a new or existing ViewModel from the ViewModelProvider.
        flightRecentViewModel = ViewModelProvider(this).get(FlightRecentViewModel::class.java)

        // Add an observer on the LiveData returned by getAlphabetizedWords.
        // The onChanged() method fires when the observed data changes and the activity is
        // in the foreground.
        flightRecentViewModel.allItems.observe(this) { words ->
            words.let { adapter.setWords(it) }
        }

    }

    @SuppressLint("ResourceType")
    override fun onClick(p0: View?) {
        when(p0?.id){
            R.id.card_Economy->{
                Toast.makeText(this,"Clicked 1",Toast.LENGTH_SHORT).show()
                card_Economy.setCardBackgroundColor(Color.parseColor("#6200EE"))
                txtEconomy.setTextColor(Color.WHITE)
                card_Business.setCardBackgroundColor(Color.TRANSPARENT)
                txtBusiness.setTextColor(Color.BLACK)
                card_First.setCardBackgroundColor(Color.TRANSPARENT)
                txtFirst.setTextColor(Color.BLACK)



            }
            R.id.card_Business->{
                Toast.makeText(this,"Clicked 2",Toast.LENGTH_SHORT).show()
                card_Business.setCardBackgroundColor(Color.parseColor("#6200EE"))
                txtBusiness.setTextColor(Color.WHITE)
                card_Economy.setCardBackgroundColor(Color.TRANSPARENT)
                txtEconomy.setTextColor(Color.BLACK)
                card_First.setCardBackgroundColor(Color.TRANSPARENT)
                txtFirst.setTextColor(Color.BLACK)
            }
            R.id.card_First->{
                Toast.makeText(this,txtFirst.text.toString(),Toast.LENGTH_SHORT).show()
                card_First.setCardBackgroundColor(Color.parseColor("#6200EE"))
                txtFirst.setTextColor(Color.WHITE)
                card_Economy.setCardBackgroundColor(Color.TRANSPARENT)
                txtEconomy.setTextColor(Color.BLACK)
                card_Business.setCardBackgroundColor(Color.TRANSPARENT)
                txtBusiness.setTextColor(Color.BLACK)
            }
        }

    }
}