package com.vmyan.myantrip.data

import com.google.firebase.firestore.FirebaseFirestore
import com.vmyan.myantrip.model.Emergency
import com.vmyan.myantrip.utils.Resource
import kotlinx.coroutines.tasks.await

class EmergencyRepositoryImpl : EmergencyRepository {
    override suspend fun getEmergencyLsit(): Resource<MutableList<Emergency>> {
        val emergencyList = mutableListOf<Emergency>()

        val resultList = FirebaseFirestore.getInstance()
            .collection("EmergencyList")
            .get()
            .await()

        for (data in resultList){
            val eid = data.id
            val city = data.getString("city")!!
            val areaCode = data.getDouble("areaCode")!!.toInt()
            val fire = data.get("fire") as ArrayList<Long>
            val electricity = data.get("electricity") as ArrayList<Long>
            val police = data.get("police") as ArrayList<Long>
            val hospital = data.get("hospital") as ArrayList<Long>
            val ambulance = data.get("ambulance") as ArrayList<Long>



            emergencyList.add(Emergency(eid, city, areaCode, fire, electricity, police, hospital, ambulance))
        }

        return Resource.Success(emergencyList)
    }

}