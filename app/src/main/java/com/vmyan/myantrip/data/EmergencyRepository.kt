package com.vmyan.myantrip.data

import com.vmyan.myantrip.model.Emergency
import com.vmyan.myantrip.utils.Resource

interface EmergencyRepository {
    suspend fun getEmergencyLsit() : Resource<MutableList<Emergency>>
}