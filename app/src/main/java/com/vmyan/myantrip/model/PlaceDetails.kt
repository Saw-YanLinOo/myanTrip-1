package com.vmyan.myantrip.model

data class PlaceDetails(
    var cat_id: String,
    var subcat_id: String,
    var place: Place,
    var reviewList: MutableList<Review>
)