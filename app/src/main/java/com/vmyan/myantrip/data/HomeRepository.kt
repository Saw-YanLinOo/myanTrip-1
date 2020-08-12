package com.vmyan.myantrip.data

import com.vmyan.myantrip.model.Place
import com.vmyan.myantrip.model.PlaceCategory
import com.vmyan.myantrip.model.SubPlaceCategory
import com.vmyan.myantrip.utils.Resource

interface HomeRepository {
    suspend fun getPlaceCategory() : Resource<MutableList<PlaceCategory>>

    suspend fun getSubPlaceCategory() : Resource<MutableList<SubPlaceCategory>>

    suspend fun getPlaceBySubCategory(sub_cat_id: String) : Resource<MutableList<Place>>
}