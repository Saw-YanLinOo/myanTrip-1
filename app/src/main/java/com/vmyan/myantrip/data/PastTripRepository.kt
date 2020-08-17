package com.vmyan.myantrip.data

import com.vmyan.myantrip.model.Trip
import com.vmyan.myantrip.utils.Resource

interface PastTripRepository {
    suspend fun getPastTripList() : Resource<MutableList<Trip>>
}