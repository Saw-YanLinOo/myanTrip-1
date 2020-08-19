package com.vmyan.myantrip.model

import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentId

data class TripPlan(
    @DocumentId
    var plan_id: String,
    var name: String,
    var img: String,
    var fromDate: Timestamp,
    var toDate: Timestamp? = null,
    var fromTime: String,
    var toTime: String,
    var fromState: String,
    var toState: String,
    var fromCity: String,
    var toCity: String,
    var fromAddress: String,
    var toAddress: String,
    var estimationCost: Int,
    var confirmation: Boolean,
    var type: String,
    var description: String,
    var details: String,
    var viewType : String
)