package com.vmyan.myantrip.data

import android.net.Uri
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.vmyan.myantrip.utils.Resource
import kotlinx.coroutines.tasks.await


class AddNewTripRepositoryImpl : AddNewTripRepository {

    private val storageRef = FirebaseStorage.getInstance().reference
    private val firestoreRef = FirebaseFirestore.getInstance()

    override suspend fun addNewTrip(
        tripImgUri: ByteArray,
        tripDestination: String,
        tripStartDate: Timestamp,
        tripEndDate: Timestamp,
        tripType: String,
        tripName: String,
        tripDesc: String,
        userId: String,
        userName: String,
        userImg: String,
        tripCost: Int
    ): Resource<String> {
        var downloadUrl = ""

        val tripRef = storageRef.child("trip/$tripName$userId${tripStartDate.toDate()}${tripEndDate.toDate()}.jpeg")
        val uploadTask = tripRef.putBytes(tripImgUri)

        val urlTask = uploadTask.continueWithTask { task ->
            if (!task.isSuccessful) {
                task.exception?.let {
                    throw it
                }
            }
            tripRef.downloadUrl
        }.addOnCompleteListener {
            downloadUrl = it.result.toString()
        }
        urlTask.await()

        val data = hashMapOf(
            "tripImg" to downloadUrl,
            "tripDestination" to tripDestination,
            "tripName" to tripName,
            "tripType" to tripType,
            "tripDesc" to tripDesc,
            "tripStartDate" to tripStartDate,
            "tripEndDate" to tripEndDate,
            "userId" to userId,
            "userName" to userName,
            "userImg" to userImg,
            "tripCost" to tripCost
        )

        firestoreRef.collection("TripList").add(data).await()

        return Resource.Success("success")
    }

}