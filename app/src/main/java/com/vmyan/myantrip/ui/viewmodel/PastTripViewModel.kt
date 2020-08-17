package com.vmyan.myantrip.ui.viewmodel

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.google.firebase.Timestamp
import com.vmyan.myantrip.data.AddNewTripRepository
import com.vmyan.myantrip.data.PastTripRepository
import com.vmyan.myantrip.data.UpComingTripRepository
import com.vmyan.myantrip.utils.Resource
import kotlinx.coroutines.Dispatchers

class PastTripViewModel (private val pastTripRepository: PastTripRepository) : ViewModel() {

    fun getPastTripList() = liveData(Dispatchers.IO) {
        emit(Resource.Loading())
        try {
            val tripList = pastTripRepository.getPastTripList()
            emit(tripList)
        }catch (e: Exception){
            e.printStackTrace()
            emit(Resource.Failure(e.message!!.toString()))
        }
    }

}
