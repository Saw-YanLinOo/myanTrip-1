package com.vmyan.myantrip.data

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.vmyan.myantrip.model.Trip
import com.vmyan.myantrip.model.TripPlan
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

        val tid = result.id
        val tripImg = result.getString("tripImg")
        val tripStartDate = result.getTimestamp("tripStartDate")
        val tripEndDate = result.getTimestamp("tripEndDate")
        val tripType = result.getString("tripType")
        val tripName = result.getString("tripName")
        val tripDestination = result.getString("tripDestination")
        val tripDesc = result.getString("tripDesc")
        val tripCost = result.getDouble("tripCost")
        val userId = result.get("userId") as ArrayList<String>

        val planList = mutableListOf<TripPlan>()
        val planResult = FirebaseFirestore.getInstance()
            .collection("/TripList/$tripId/Plan")
            .orderBy("date",Query.Direction.ASCENDING)
            .get()
            .await()

        for (plan in planResult){
            val planId = plan.id
            val name = plan.getString("name")
            val img = plan.getString("img")
            val date = plan.getTimestamp("date")
            val state = plan.getString("state")
            val city = plan.getString("city")
            val address = plan.getString("address")
            val estimationCost = plan.getDouble("estimationCost")!!.toInt()
            val confirmation = plan.getBoolean("confirmation")
            val type = plan.getString("type")
            val description = plan.getString("description")
            val details = plan.getString("details")
            val viewType = plan.getDouble("viewType")!!.toInt()
            val status = plan.getString("status")

            planList.add(TripPlan(
                planId,
                name!!,
                img!!,
                date!!,
                state!!,
                city!!,
                address!!,
                estimationCost,
                confirmation!!,
                type!!,
                description!!,
                details!!,
                viewType,
                status!!
            ))

        }

        return Resource.Success(TripWithPlan(Trip(tid,tripImg!!,tripStartDate!!,tripEndDate!!,tripType!!,tripName!!,tripDestination!!,tripDesc!!,tripCost!!.toInt(),userId,)
            , planList))

    }

}