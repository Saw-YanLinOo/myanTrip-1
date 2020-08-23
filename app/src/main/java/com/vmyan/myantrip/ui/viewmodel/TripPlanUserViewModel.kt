package com.vmyan.myantrip.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.google.firebase.Timestamp
import com.vmyan.myantrip.data.TripPlanUserRepository
import com.vmyan.myantrip.utils.Resource
import kotlinx.coroutines.Dispatchers

class TripPlanUserViewModel (private val tripPlanUserRepository: TripPlanUserRepository) : ViewModel() {

    fun getTeammates(
        tripId: String
    ) = liveData(Dispatchers.IO) {
        emit(Resource.Loading())
        try {
            val userList = tripPlanUserRepository.getTeammates(tripId)
            emit(userList)
        }catch (e: Exception){
            emit(Resource.Failure(e.cause!!.toString()))
        }
    }

    fun addTeammates(
        tripId: String,
        email: String
    ) = liveData(Dispatchers.IO) {
        emit(Resource.Loading())
        try {
            val result = tripPlanUserRepository.addTeammate(tripId,email)
            emit(result)
        }catch (e: Exception){
            e.printStackTrace()
            emit(Resource.Failure(e.cause!!.toString()))
        }
    }

}
