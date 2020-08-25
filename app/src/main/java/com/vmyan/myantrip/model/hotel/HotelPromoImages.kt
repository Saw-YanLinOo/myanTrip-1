package com.vmyan.myantrip.model.hotel

import com.google.firebase.firestore.DocumentId

data class HotelPromoImages(
    @DocumentId
    val hotelImagePromoId : String,
    val hotelImagePro : ArrayList<String>
)