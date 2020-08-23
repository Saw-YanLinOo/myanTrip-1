package com.vmyan.myantrip.data

import com.vmyan.myantrip.model.User
import com.vmyan.myantrip.utils.Resource

interface TripPlanUserRepository {
    suspend fun getTeammates(tripId: String) : Resource<MutableList<User>>
    suspend fun addTeammate(tripId: String, userEmail: String) : Resource<String>
}