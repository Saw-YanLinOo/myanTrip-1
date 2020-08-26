package com.vmyan.myantrip.data

import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
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

}