package com.vmyan.myantrip.data

import com.vmyan.myantrip.model.TripWithPlan
import com.vmyan.myantrip.utils.Resource

interface TripPlanRepository {
    suspend fun getTripPlan(tripId: String) : Resource<TripWithPlan>
}