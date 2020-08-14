package com.vmyan.myantrip.model

data class PlaceDetails(
    var place: Place,
    var reviewList: MutableList<Review>
)