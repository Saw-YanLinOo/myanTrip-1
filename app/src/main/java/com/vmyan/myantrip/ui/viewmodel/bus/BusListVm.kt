package com.vmyan.myantrip.ui.viewmodel.bus

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.vmyan.myantrip.data.booking.carRental.bus.BusListRepository
import com.vmyan.myantrip.utils.Resource

import kotlinx.coroutines.Dispatchers

class BusListVm (private val busListRepository: BusListRepository) : ViewModel(){
    fun fetchBusAllList() = liveData(Dispatchers.IO) {
        emit(Resource.Loading())
        try {
            val carList = busListRepository.getAllBusList()
            emit(carList)
        } catch (e: Exception) {
            emit(Resource.Failure(e.message!!))
        }
    }

}
