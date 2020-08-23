package com.vmyan.myantrip.ui.booking.bus.carRental

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.vmyan.myantrip.R
import kotlinx.android.synthetic.main.activity_car_rental.*

class CarRental : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_car_rental)
        card_CarRentailSearch.setOnClickListener {
            val intent= Intent (this, CarRentalsListView::class.java)
            startActivity(intent)

        }
    }
}