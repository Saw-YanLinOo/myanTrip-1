package com.vmyan.myantrip.model.bookingCate

import com.google.firebase.firestore.DocumentId

data class BookingCateItem(
    @DocumentId
    val cateId : String,
    val txtBookingName:String,
    val imgBooking : String
)