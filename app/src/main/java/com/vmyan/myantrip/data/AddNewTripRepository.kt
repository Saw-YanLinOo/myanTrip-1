package com.vmyan.myantrip.data

import android.net.Uri
import com.google.firebase.Timestamp
import com.vmyan.myantrip.utils.Resource

interface AddNewTripRepository {
    suspend fun addNewTrip(
        tripImgUri: ByteArray,
        tripDestination: String,
        tripStartDate: Timestamp,
        tripEndDate: Timestamp,
        tripType: String,
        tripName: String,
        tripDesc: String,
        userId: ArrayList<String>,
        tripCost: Int
    ) : Resource<String>
}