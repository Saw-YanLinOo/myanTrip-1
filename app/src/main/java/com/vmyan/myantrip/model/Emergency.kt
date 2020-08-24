package com.vmyan.myantrip.model

import com.google.firebase.firestore.DocumentId

data class Emergency(
    @DocumentId
    var eid: String,
    var city: String,
    var areaCode: Int,
    var fire: ArrayList<Long>,
    var electricity: ArrayList<Long>,
    var police: ArrayList<Long>,
    var hospital: ArrayList<Long>,
    var ambulance: ArrayList<Long>
)