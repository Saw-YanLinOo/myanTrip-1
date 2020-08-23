package com.vmyan.myantrip.ui.viewmodel.carRental

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.vmyan.myantrip.data.booking.carRental.CarListRepository

class CarListVMFactory  (private val carListRepository: CarListRepository) : ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(CarListRepository ::class.java).newInstance(carListRepository)
    }

}