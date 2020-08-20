package com.vmyan.myantrip.data

import com.google.firebase.Timestamp
import com.vmyan.myantrip.utils.Resource

interface AddPlanRepository {

    suspend fun addPlan(
        tripId: String,
        name: String,
        img: String,
        date: Timestamp,
        state: String,
        city: String,
        address: String,
        estimationCost: Int,
        confirmation: Boolean,
        type: String,
        description: String,
        details: String,
        viewType : Int,
        status: String
    ): Resource<String>
}