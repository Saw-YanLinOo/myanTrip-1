package com.vmyan.myantrip.model

import com.google.firebase.firestore.DocumentId

data class SubPlaceCategory(
    @DocumentId
    var sub_cat_id: String,
    var name: String
)