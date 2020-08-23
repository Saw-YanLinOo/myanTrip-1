package com.vmyan.myantrip.ui.booking.bus

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.vmyan.myantrip.R
import kotlinx.android.synthetic.main.activity_bus_booking.*

class BusBooking : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bus_booking)
        card_BusSearch.setOnClickListener {
            val intent = Intent (this, BusListView::class.java)
            startActivity(intent)
        }
    }
}