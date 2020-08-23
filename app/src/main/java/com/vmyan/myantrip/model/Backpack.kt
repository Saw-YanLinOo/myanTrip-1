package com.vmyan.myantrip.model

import com.google.firebase.firestore.DocumentId

data class Backpack(
    @DocumentId
    var bid: String,
    var name: String,
    var count: Int,
    var status: Boolean
)