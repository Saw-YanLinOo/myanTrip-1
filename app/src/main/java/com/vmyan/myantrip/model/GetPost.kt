package com.vmyan.myantrip.model

import com.google.firebase.firestore.DocumentId

class GetPost (
    @DocumentId
    var user : User,
    var posts: Posts
)