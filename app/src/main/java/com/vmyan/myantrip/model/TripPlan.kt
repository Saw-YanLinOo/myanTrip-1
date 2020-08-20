package com.vmyan.myantrip.model

import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentId

data class TripPlan(
    @DocumentId
    var plan_id: String,
    var name: String,
    var img: String,
    var date: Timestamp,
    var state: String,
    var city: String,
    var address: String,
    var estimationCost: Int,
    var confirmation: Boolean,
    var type: String,
    var description: String,
    var details: String,
    var viewType : Int,
    var status: String
)