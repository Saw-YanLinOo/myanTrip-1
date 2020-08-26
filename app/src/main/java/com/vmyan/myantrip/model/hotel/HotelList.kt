package com.vmyan.myantrip.model.hotel

import com.google.firebase.firestore.DocumentId

data class HotelList(
    @DocumentId
    val hotelId: String,
    val hotelName:String,
    val hotel_address: String,
    val hotel_city: String,
    val hotel_country: String,
    val ratingValue: String,
    val lowestPrice: Long,
    val hotel_PhoneNo: String,
    val hotel_image :String,

    val wifi: Boolean,
    val breakFast: Boolean,
    val carPack: Boolean,
    val waterPool: Boolean,
    val restaurant: Boolean,
    var roomImages : ArrayList<String>

)
