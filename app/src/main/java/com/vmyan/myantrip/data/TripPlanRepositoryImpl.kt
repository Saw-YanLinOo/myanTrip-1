package com.vmyan.myantrip.data

import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import com.vmyan.myantrip.model.Trip
import com.vmyan.myantrip.model.TripWithPlan
import com.vmyan.myantrip.utils.Resource
import kotlinx.coroutines.tasks.await

class TripPlanRepositoryImpl : TripPlanRepository {
    override suspend fun getTripPlan(tripId: String): Resource<TripWithPlan> {
        val result = FirebaseFirestore.getInstance()
            .collection("TripList")
            .document(tripId)
            .get()
            .await()

        val tripId = result.id
        val tripImg = result.getString("tripImg")
        val tripStartDate = result.getTimestamp("tripStartDate")
        val tripEndDate = result.getTimestamp("tripEndDate")
        val tripType = result.getString("tripType")
        val tripName = result.getString("tripName")
        val tripDestination = result.getString("tripDestination")
        val tripDesc = result.getString("tripDesc")
        val tripCost = result.getDouble("tripCost")
        val userId = result.getString("userId")
        val userImg = result.getString("userImg")
        val userName = result.getString("userName")

        return Resource.Success(TripWithPlan(Trip(tripId,tripImg!!,tripStartDate!!,tripEndDate!!,tripType!!,tripName!!,tripDestination!!,tripDesc!!,tripCost!!.toInt(),userId!!,userImg!!,userName!!), mutableListOf()))

    }

}