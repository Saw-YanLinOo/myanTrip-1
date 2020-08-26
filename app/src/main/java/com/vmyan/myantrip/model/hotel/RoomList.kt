package com.vmyan.myantrip.model.hotel

import com.google.firebase.firestore.DocumentId

data class RoomList(
    @DocumentId
    val roomId : String,
    val roomType : String,
    val roomPrice : Long,

    val roomWifi : Boolean,

    val roomAirCon : Boolean,
    val roomWithToilet : Boolean


)