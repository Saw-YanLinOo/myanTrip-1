package com.vmyan.myantrip.model

import com.google.firebase.firestore.DocumentId

class GetProfile (
    @DocumentId
    var user : User,
    var post : List<Posts>
)