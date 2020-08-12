package com.vmyan.myantrip.data

import com.vmyan.myantrip.model.Place
import com.vmyan.myantrip.utils.Resource

interface PlaceDetailsRepository {
    suspend fun getPlaceDetails(place_id: String) : Resource<MutableList<Place>>
}