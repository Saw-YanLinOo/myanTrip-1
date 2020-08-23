package com.vmyan.myantrip.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.vmyan.myantrip.data.TripBackPackRepository
import com.vmyan.myantrip.utils.Resource
import kotlinx.coroutines.Dispatchers

class TripBackPackViewModel (private val tripBackPackRepository: TripBackPackRepository) : ViewModel() {

    fun addBackpack(
        tripId: String,
        name: String,
        count: Int
    ) = liveData(Dispatchers.IO) {
        emit(Resource.Loading())
        try {
            val result = tripBackPackRepository.addBackpack(tripId, name, count)
            emit(result)
        }catch (e: Exception){
            emit(Resource.Failure(e.cause!!.toString()))
        }
    }

    fun getBackpackList(
        tripId: String
    ) = liveData(Dispatchers.IO) {
        emit(Resource.Loading())
        try {
            val result = tripBackPackRepository.getBackpackList(tripId)
            emit(result)
        }catch (e: Exception){
            emit(Resource.Failure(e.cause!!.toString()))
        }
    }

    fun updateCheck(
        tripId: String,
        bid: String,
        status: Boolean
    ) = liveData(Dispatchers.IO) {
        emit(Resource.Loading())
        try {
            val result = tripBackPackRepository.updateCheck(tripId,bid, status)
            emit(result)
        }catch (e: Exception){
            emit(Resource.Failure(e.cause!!.toString()))
        }
    }

}
