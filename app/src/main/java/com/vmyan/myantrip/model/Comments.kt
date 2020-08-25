package com.vmyan.myantrip.model

import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentId

class Comments (
    @DocumentId
    var user: User,
    var description : String? = "",
    var time : Timestamp? = Timestamp.now()

)
