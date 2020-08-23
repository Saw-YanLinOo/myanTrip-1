package com.vmyan.myantrip.data

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.vmyan.myantrip.model.Backpack
import com.vmyan.myantrip.utils.Resource
import kotlinx.coroutines.tasks.await

class TripBackPackRepositoryImpl : TripBackPackRepository {
    override suspend fun addBackpack(tripId: String, name: String, count: Int): Resource<String> {
        val data = hashMapOf(
            "name" to name,
            "count" to count,
            "status" to false
        )

        FirebaseFirestore.getInstance().collection("/TripList/$tripId/Backpack").add(data).await()

        return Resource.Success("success")
    }

    override suspend fun getBackpackList(tripId: String): Resource<MutableList<Backpack>> {
        val backPackList = mutableListOf<Backpack>()
        val resultList = FirebaseFirestore.getInstance()
            .collection("/TripList/$tripId/Backpack")
            .orderBy("status",Query.Direction.ASCENDING)
            .get()
            .await()

        for (data in resultList){
            backPackList.add(
                Backpack(
                    data.id,
                    data.getString("name")!!,
                    data.getDouble("count")!!.toInt(),
                    data.getBoolean("status")!!
                )
            )
        }

        return Resource.Success(backPackList)
    }

    override suspend fun updateCheck(tripId: String, bid: String, status: Boolean): Resource<String> {
        FirebaseFirestore.getInstance().collection("/TripList/$tripId/Backpack")
            .document(bid)
            .update("status",status)
            .await()
        return Resource.Success("success")
    }

}