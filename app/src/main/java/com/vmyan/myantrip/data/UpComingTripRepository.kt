package com.vmyan.myantrip.data

import com.vmyan.myantrip.model.Trip
import com.vmyan.myantrip.utils.Resource

interface UpComingTripRepository {
    suspend fun getUpComingTripList() : Resource<MutableList<Trip>>
}