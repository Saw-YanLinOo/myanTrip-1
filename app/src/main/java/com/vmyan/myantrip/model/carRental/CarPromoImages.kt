package com.vmyan.myantrip.model.carRental

import com.google.firebase.firestore.DocumentId

data class CarPromoImages(
    @DocumentId
    val carPromoId :String,
    val carPromoImages : ArrayList<String>
)