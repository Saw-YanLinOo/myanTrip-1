package com.vmyan.myantrip.model

data class PlaceByCategory(
    var subPlaceCategory: SubPlaceCategory,
    var placeList: MutableList<Place>
)