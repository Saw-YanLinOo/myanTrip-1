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
    ): Resource<String> {
        val data = hashMapOf(
            "name" to name,
            "img" to img,
            "date" to date,
            "state" to state,
            "city" to city,
            "address" to address,
            "estimationCost" to estimationCost,
            "confirmation" to confirmation,
            "type" to type,
            "description" to description,
            "details" to details,
            "viewType" to viewType,
            "status" to status
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