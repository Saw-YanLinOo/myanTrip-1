package com.vmyan.myantrip.model

import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentId

data class Trip(
    @DocumentId
    var tripId: String,
    var tripImg: String,
    var tripStartDate: Timestamp,
    var tripEndDate: Timestamp,
    var tripType: String,
    var tripName: String,
    var tripDestination: String,
    var tripDesc: String,
    var tripCost: Int,
    var userId: String,
    var userImg: String,
    var userName: String

)