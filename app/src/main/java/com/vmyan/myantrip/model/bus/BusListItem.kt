package com.vmyan.myantrip.model.bus

import com.google.firebase.firestore.DocumentId

data class BusListItem(
    @DocumentId
    val busId : String,
    val busName : String,
    val busStatus : String,
    val busFrom : String,
    val busTo : String,
    val busFromTo : String,
    val busDeparture : String,
    val busTimeStart : String,
    val busTimeStop : String,
    val busPricePerSeat : String,
    val busTotalNoSeat : String,
    val busImage : String

)
