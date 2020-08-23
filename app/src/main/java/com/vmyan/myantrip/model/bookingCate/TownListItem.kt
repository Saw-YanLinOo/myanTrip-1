package com.vmyan.myantrip.model.bookingCate

import com.google.firebase.firestore.DocumentId

data class TownListItem(
    @DocumentId
    var townId : String,
    var townName: String,
    var townImage :String
)