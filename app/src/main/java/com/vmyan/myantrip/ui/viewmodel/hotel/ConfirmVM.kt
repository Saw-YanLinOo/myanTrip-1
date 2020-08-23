package com.vmyan.myantrip.ui.viewmodel.hotel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.vmyan.myantrip.data.booking.carRental.hotel.ConfirmRepository
import com.vmyan.myantrip.utils.Resource
import kotlinx.coroutines.Dispatchers

class ConfirmVM (private val confirmRepository: ConfirmRepository): ViewModel(){
    fun fectchConfirmDetails(id :String,cid: String,room_id :String) = liveData(Dispatchers.IO) {
        emit(Resource.Loading())
        try {
            val conFirmList = confirmRepository.getConfirmDetails(id, cid, room_id)
            emit(conFirmList)
        } catch (e: Exception) {
            emit(Resource.Failure(e.message!!))
        }
    }

}