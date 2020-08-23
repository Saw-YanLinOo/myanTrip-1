package com.vmyan.myantrip.ui.viewmodel.carRental

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.vmyan.myantrip.data.booking.carRental.CarListRepository
import com.vmyan.myantrip.utils.Resource
import kotlinx.coroutines.Dispatchers

class CarListVM  (private val carListRepository: CarListRepository) : ViewModel(){
    fun fetchCarList() = liveData(Dispatchers.IO) {
        emit(Resource.Loading())
        try {
            val carList = carListRepository.getCarRentailsList()
            emit(carList)
        } catch (e: Exception) {
            emit(Resource.Failure(e.message!!))
        }
    }
}