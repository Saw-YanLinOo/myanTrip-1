package com.vmyan.myantrip.model

import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentId

data class Posts (
    @DocumentId
    var id : String,
    var user_id: String,
    var description: String,
    var image_url:ArrayList<String>,
    var like : String,
    var share : String,
    var time : Timestamp
)
