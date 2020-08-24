package com.vmyan.myantrip.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.vmyan.myantrip.data.EmergencyRepository
import com.vmyan.myantrip.utils.Resource
import kotlinx.coroutines.Dispatchers

class EmergencyViewModel (private val emergencyRepository: EmergencyRepository) : ViewModel() {

    fun getEmergencyList() = liveData(Dispatchers.IO) {
        emit(Resource.Loading())
        try {
            val result = emergencyRepository.getEmergencyLsit()
            emit(result)
        }catch (e: Exception){
            e.printStackTrace()
            emit(Resource.Failure(e.message.toString()))
        }
    }

}