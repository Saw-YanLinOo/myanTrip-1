package com.vmyan.myantrip.model

import com.google.firebase.firestore.DocumentId

class UserAccountSetting (
    @DocumentId
    var description:String,
    var followers:Long,
    var followings:Long,
    var posts:Long,
    var profilephoto:String,
    var username:String,
    var website:String
)
