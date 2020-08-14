package com.vmyan.myantrip.model

import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentId

data class Review(
    @DocumentId
    var review_id: String,
    var user_id: String,
    var user_name: String,
    var user_img: String,
    var rating_val: Float,
    var desc: String,
    var date: Timestamp
)