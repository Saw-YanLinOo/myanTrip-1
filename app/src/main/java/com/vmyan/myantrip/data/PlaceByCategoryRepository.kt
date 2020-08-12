package com.vmyan.myantrip.data

import com.vmyan.myantrip.model.PlaceByCategory
import com.vmyan.myantrip.utils.Resource

interface PlaceByCategoryRepository {
    suspend fun getPlaceByCategory(cat_id: String) : Resource<MutableList<PlaceByCategory>>
}