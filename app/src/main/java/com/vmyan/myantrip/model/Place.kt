package com.vmyan.myantrip.model

import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.GeoPoint

data class Place(
    @DocumentId
    var place_id: String,
    var address: String,
    var buildDate: String,
    var category: String,
    var city: String,
    var country: String,
    var founder: String,
    var gallery: ArrayList<String>,
    var history: String,
    var info: String,
    var latlng: GeoPoint,
    var mainImg: String,
    var name: String,
    var ratingValue: Float,
    var sliderImg: ArrayList<String>,
    var state: String
)