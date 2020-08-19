package com.vmyan.myantrip.model

data class TripWithPlan(
    var trip: Trip,
    var planList: MutableList<TripPlan>
)