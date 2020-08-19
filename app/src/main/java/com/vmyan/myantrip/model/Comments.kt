package com.vmyan.myantrip.model

import com.google.firebase.firestore.DocumentId

class Comments (
    @DocumentId
    var username : String,
    var profilephoto : String,
    var description : String
)
