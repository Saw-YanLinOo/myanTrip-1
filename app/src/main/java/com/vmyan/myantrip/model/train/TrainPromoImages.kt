package com.vmyan.myantrip.model.train

import com.google.firebase.firestore.DocumentId

data class TrainPromoImages(
    @DocumentId
    val trainImagePromoId : String,
    val trainImagePro : ArrayList<String>
)