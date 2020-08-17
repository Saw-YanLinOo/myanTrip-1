package com.vmyan.myantrip.data

import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.vmyan.myantrip.model.Trip
import com.vmyan.myantrip.utils.Resource
import kotlinx.coroutines.tasks.await

class PastTripRepositoryImpl : PastTripRepository {
    override suspend fun getPastTripList() : Resource<MutableList<Trip>> {
        val tripList = mutableListOf<Trip>()

        val resultList = FirebaseFirestore.getInstance()
            .collection("TripList")
            .whereLessThan("tripEndDate", Timestamp.now())
            .orderBy("tripEndDate",Query.Direction.DESCENDING)
            .get()
            .await()

        for (doc in resultList){
            val tripId = doc.id
            val tripImg = doc.getString("tripImg")
            val tripStartDate = doc.getTimestamp("tripStartDate")
            val tripEndDate = doc.getTimestamp("tripEndDate")
            val tripType = doc.getString("tripType")
            val tripName = doc.getString("tripName")
            val tripDestination = doc.getString("tripDestination")
            val tripDesc = doc.getString("tripDesc")
            val userId = doc.getString("userId")
            val userImg = doc.getString("userImg")
            val userName = doc.getString("userName")


            tripList.add(Trip(tripId,tripImg!!,tripStartDate!!,tripEndDate!!,tripType!!,tripName!!,tripDestination!!,tripDesc!!,userId!!,userImg!!,userName!!))
        }

        return Resource.Success(tripList)
    }

}