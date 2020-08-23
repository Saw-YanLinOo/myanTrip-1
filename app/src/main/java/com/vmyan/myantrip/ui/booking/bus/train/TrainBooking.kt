package com.vmyan.myantrip.ui.booking.bus.train

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.vmyan.myantrip.R
import kotlinx.android.synthetic.main.activity_train_booking.*

class TrainBooking : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_train_booking)
        card_TrainSearch.setOnClickListener {
            val intent =Intent(this, TrainViewList::class.java)
            startActivity(intent)
        }
    }
}