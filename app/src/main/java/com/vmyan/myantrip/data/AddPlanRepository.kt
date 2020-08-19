package com.vmyan.myantrip.data

import com.google.firebase.Timestamp
import com.vmyan.myantrip.utils.Resource

interface AddPlanRepository {

    suspend fun addPlan(
        tripId: String,
        name: String,
        img: String,
        fromDate: Timestamp,
        toDate: Timestamp?,
        fromTime: String,
        toTime: String,
        fromState: String,
        toState: String,
        fromCity: String,
        toCity: String,
        fromAddress: String,
        toAddress: String,
        estimationCost: Int,
        confirmation: Boolean,
        type: String,
        description: String,
        details: String,
        viewType : String
    ): Resource<String>
}