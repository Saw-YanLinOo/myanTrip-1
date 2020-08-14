package com.vmyan.myantrip.data

import com.google.firebase.Timestamp
import com.vmyan.myantrip.model.Place
import com.vmyan.myantrip.model.PlaceDetails
import com.vmyan.myantrip.utils.Resource

interface PlaceDetailsRepository {
    suspend fun getPlaceDetails(place_id: String) : Resource<MutableList<PlaceDetails>>
    suspend fun getNearByPlace(city: String) : Resource<MutableList<Place>>
    suspend fun addReview(cat_name: String, subcat_id: String, place_id: String, userid: String, username: String, userImg: String, rating: Float, desc: String, date: Timestamp) : Resource<String>
}