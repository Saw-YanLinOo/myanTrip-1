package com.vmyan.myantrip.model

import com.google.firebase.firestore.DocumentId

data class PlaceCategory(
    @DocumentId
    var cat_id: String,
    var cat_name: String,
    var cat_img_url: String
)