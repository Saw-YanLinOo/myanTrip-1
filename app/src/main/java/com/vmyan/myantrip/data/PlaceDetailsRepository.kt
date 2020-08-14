package com.vmyan.myantrip.data

import com.vmyan.myantrip.model.Place
import com.vmyan.myantrip.model.PlaceDetails
import com.vmyan.myantrip.utils.Resource

interface PlaceDetailsRepository {
    suspend fun getPlaceDetails(place_id: String) : Resource<MutableList<PlaceDetails>>
    suspend fun getNearByPlace(city: String) : Resource<MutableList<Place>>
}