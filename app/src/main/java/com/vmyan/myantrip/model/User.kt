package com.vmyan.myantrip.model

import com.google.firebase.firestore.DocumentId

data class User (
    @DocumentId
    var user_id:String,
    var phone_number:String,
    var email:String,
    var username:String,
    var profilephoto:String,
    var backgroudphoto : String?,
    var followers : Long? = 0,
    var followings : Long? = 0
)