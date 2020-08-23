package com.vmyan.myantrip.model.train

import com.google.firebase.firestore.DocumentId

data class TrainListItem(
    @DocumentId
    val trainId : String,
    val tranNo : String,
    val trainFrom : String,
    val trainTo : String,
    val trainFromTo : String,
    val trainDeparture : String,
    val trainTimeStart : String,
    val trainTimeStop : String,
    val trainStatus : String,
    val trainImage : String,
    val trainPerSeatPrice : String

)