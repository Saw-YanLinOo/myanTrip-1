package com.vmyan.myantrip.model.bookingCate

import com.google.firebase.firestore.DocumentId

data class PromoSliderItem(
    @DocumentId
    var promo_id: String,
    var imgUrl: ArrayList<String>
)