package com.vmyan.myantrip.model.hotel

import com.google.firebase.firestore.DocumentId

data class BankCard(
    @DocumentId
    val bankId : String,
    val bankName : String,
    val bankLogo : String,
    val bankCardType : String
)