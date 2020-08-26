package com.vmyan.myantrip.data

import com.google.firebase.Timestamp
import com.vmyan.myantrip.utils.Resource

interface BookingTicketRepository {
    suspend fun addTicket(
        dateFrom: String,
        dateTo: String,
        totalCost: Long,
        tName: String,
        tTypeImage: String
    ) : Resource<String>
}