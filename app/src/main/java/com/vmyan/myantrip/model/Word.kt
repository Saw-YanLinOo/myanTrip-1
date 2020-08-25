package com.vmyan.myantrip.model

import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.Exclude
import com.google.firebase.firestore.IgnoreExtraProperties

@IgnoreExtraProperties
data class Word (
    var category: String? = "",
    var eng: String? = "",
    var eng_file: String? = "",
    var id: String? = "",
    var myn: String? = "",
    var myn_file: String? = ""
)