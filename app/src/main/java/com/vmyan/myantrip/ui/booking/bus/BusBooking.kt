package com.vmyan.myantrip.ui.booking.bus

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.vmyan.myantrip.R
import kotlinx.android.synthetic.main.activity_bus_booking.*
import kotlinx.android.synthetic.main.activity_hotel_booking.*

class BusBooking : AppCompatActivity() {
    private var location : String=""
    private var cityImagge : String=""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bus_booking)
            pickPlace()
        card_BusSearch.setOnClickListener {
            val intent = Intent (this, BusListView::class.java)
            startActivity(intent)
        }
    }
    private fun pickPlace() {
        llBusFrom.setOnClickListener {
            cityFromOpenPostActivityCustom.launch(1)
           // location=appComTxtPickLoacation.text.toString()

        }
        llBusTo.setOnClickListener {
            cityToOpenPostActivityCustom.launch(1)
            //location=appComTxtPickLoacation.text.toString()
        }
    }
    private val cityFromOpenPostActivityCustom =
        registerForActivityResult(CityFromPostActivityContract()) { result ->
            if (result != null) {
                txt_BusFrom.text = result[0]
                Glide.with(this).load(result[1]).into(imgBusFrom)
                location = result[0]
                cityImagge=result[1]
            }
        }
    private val cityToOpenPostActivityCustom =
        registerForActivityResult(CityToPostActivityContract()) { result ->
            if (result != null) {
                txt_BusTo.text = result[0]
                Glide.with(this).load(result[1]).into(imgBusTo)
                location = result[0]
                cityImagge=result[1]
            }
        }
}