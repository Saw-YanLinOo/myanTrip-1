package com.vmyan.myantrip.ui.viewmodel

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.google.firebase.Timestamp
import com.vmyan.myantrip.data.AddNewTripRepository
import com.vmyan.myantrip.utils.Resource
import kotlinx.coroutines.Dispatchers

class AddNewTripViewModel (private val addNewTripRepository: AddNewTripRepository) : ViewModel() {

    fun addNewTrip(
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
    ) = liveData(Dispatchers.IO) {
        emit(Resource.Loading())
        try {
            val addNewTripResult = addNewTripRepository.addNewTrip(tripImgUri,tripDestination, tripStartDate, tripEndDate, tripType, tripName, tripDesc, userId, userName, userImg, tripCost)
            emit(addNewTripResult)
        }catch (e: Exception){
            emit(Resource.Failure(e.cause!!.toString()))
        }
    }

}
