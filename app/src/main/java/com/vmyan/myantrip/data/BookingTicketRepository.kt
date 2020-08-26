package com.vmyan.myantrip.data

import com.google.firebase.Timestamp
import com.vmyan.myantrip.model.BookingTicket
import com.vmyan.myantrip.utils.Resource

interface BookingTicketRepository {
    suspend fun addTicket(
        dateFrom: String,
        dateTo: String,
        totalCost: Long,
        tName: String,
        tTypeImage: String
    ) : Resource<String>

    suspend fun getPreTicketList() : Resource.Success<MutableList<BookingTicket>>
    suspend fun getExpTicketList() : Resource.Success<MutableList<BookingTicket>>
}