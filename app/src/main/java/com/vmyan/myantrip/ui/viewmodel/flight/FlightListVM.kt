package com.vmyan.myantrip.ui.viewmodel.flight

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.vmyan.myantrip.data.booking.carRental.flight.FlightListRepository
import com.vmyan.myantrip.utils.Resource
import kotlinx.coroutines.Dispatchers

class FlightListVM (private val flightListRepository: FlightListRepository) : ViewModel(){

    fun fetchFlightList() = liveData(Dispatchers.IO) {
        emit(Resource.Loading())
        try {
            val flightList = flightListRepository.getFlightList()
            emit(flightList)
        } catch (e: Exception) {
            emit(Resource.Failure(e.message!!))
        }
    }
}