package com.vmyan.myantrip.model

import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentId

data class Posts (
    @DocumentId
    var id : String? = "",
    var user_id: String? = "",
    var place_id : String? = "",
    var description: String? = "",
    var image_url:ArrayList<String>,
    var like : Long? = 0,
    var unlike : Long? = 0,
    var share : Long? = 0,
    var time : Timestamp? = Timestamp.now(),
    var comments : Long? = 0,
    var type : String? = "description"
)
