package com.vmyan.myantrip.data

import com.vmyan.myantrip.model.Place
import com.vmyan.myantrip.utils.Resource

interface SearchPlaceRepository {
    suspend fun getPlaceBySearch() : Resource<MutableList<Place>>
}