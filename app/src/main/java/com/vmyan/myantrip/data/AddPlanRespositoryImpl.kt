package com.vmyan.myantrip.data

import com.google.firebase.Timestamp
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.vmyan.myantrip.utils.Resource
import kotlinx.coroutines.tasks.await

class AddPlanRepositoryImpl : AddPlanRepository {
    override suspend fun addPlan(
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
        viewType: String
    ): Resource<String> {
        val data = hashMapOf(
            "name" to name,
            "img" to img,
            "fromDate" to fromDate,
            "toDate" to toDate,
            "fromTime" to fromTime,
            "toTime" to toTime,
            "fromState" to fromState,
            "toState" to toState,
            "fromCity" to fromCity,
            "toCity" to toCity,
            "fromAddress" to toAddress,
            "estimationCost" to estimationCost,
            "confirmation" to confirmation,
            "type" to type,
            "description" to description,
            "details" to details,
            "viewType" to viewType
        )
        FirebaseFirestore.getInstance()
            .collection("/TripList/$tripId/Plan")
            .add(data)
            .await()

        FirebaseFirestore.getInstance()
            .collection("TripList")
            .document(tripId)
            .update("tripCost",FieldValue.increment(estimationCost.toDouble()))

        return Resource.Success("success")
    }

}