package com.vmyan.myantrip.model.hotel

import com.google.firebase.firestore.DocumentId

data class RoomList(
    @DocumentId
    val roomId : String,
    val roomType : String,
    val roomPrice : String,

    val roomWifi : Boolean,

    val roomAirCon : Boolean,
    val roomWithToilet : Boolean


)