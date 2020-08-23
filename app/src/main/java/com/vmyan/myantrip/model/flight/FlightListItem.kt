package com.vmyan.myantrip.model.flight

import com.google.firebase.firestore.DocumentId

data class FlightListItem(
        @DocumentId
        val flightId : String,
        val flightName : String,
        val flightFrom : String,
        val flightTo : String,
        val flightDeparture : String,
        val fTimeStart : String,
        val fTimeStop : String,
        val flightPrice : String,
        val flightClass : String,
        val fBassageAllow : String,
        val flightImage : String


)









