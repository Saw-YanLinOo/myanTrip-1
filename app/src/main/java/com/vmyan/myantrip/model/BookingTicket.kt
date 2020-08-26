package com.vmyan.myantrip.model

import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentId

data class BookingTicket(
    @DocumentId
    var btId: String,
    var dateFrom: String,
    var dateTo: String,
    var totalCost: Long,
    var tName: String,
    var tTypeImage: String
)