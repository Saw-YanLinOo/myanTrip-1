package com.vmyan.myantrip.data

import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.vmyan.myantrip.model.BookingTicket
import com.vmyan.myantrip.utils.Resource
import kotlinx.coroutines.tasks.await

class BookingTicketRepositoryImpl : BookingTicketRepository {
    override suspend fun addTicket(
        dateFrom: String,
        dateTo: String,
        totalCost: Long,
        tName: String,
        tTypeImage: String
    ): Resource<String> {
        val data = hashMapOf(
            "dateFrom" to dateFrom,
            "dateTo" to dateTo,
            "totalCost" to totalCost,
            "tName" to tName,
            "tTypeImage" to tTypeImage
        )

        FirebaseFirestore.getInstance().collection("BookingTicket").add(data).await()
        return Resource.Success("success")
    }

    override suspend fun getPreTicketList(): Resource.Success<MutableList<BookingTicket>> {
        val preList = mutableListOf<BookingTicket>()
        val result = FirebaseFirestore.getInstance()
            .collection("BookingTicket")
            .orderBy("dateTo",Query.Direction.ASCENDING)
            .get().await()

        for (data in result){
            val id = data.id
            val from = data.getString("dateFrom")!!
            val to = data.getString("dateTo")!!
            val totalCost = data.getLong("totalCost")!!
            val tName = data.getString("tName")!!
            val tTypeImage = data.getString("tTypeImage")!!

            preList.add(BookingTicket(id,from,to,totalCost,tName, tTypeImage))
        }

        return Resource.Success(preList)
    }

    override suspend fun getExpTicketList(): Resource.Success<MutableList<BookingTicket>> {
        val expList = mutableListOf<BookingTicket>()
        val result = FirebaseFirestore.getInstance()
            .collection("BookingTicket")
            .orderBy("dateTo",Query.Direction.ASCENDING)
            .get().await()

        for (data in result){
            val id = data.id
            val from = data.getString("dateFrom")!!
            val to = data.getString("dateTo")!!
            val totalCost = data.getLong("totalCost")!!
            val tName = data.getString("tName")!!
            val tTypeImage = data.getString("tTypeImage")!!

            expList.add(BookingTicket(id,from,to,totalCost,tName, tTypeImage))
        }

        return Resource.Success(expList)
    }

}