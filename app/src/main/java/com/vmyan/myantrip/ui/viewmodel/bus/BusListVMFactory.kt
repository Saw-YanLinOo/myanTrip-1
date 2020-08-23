package com.vmyan.myantrip.ui.viewmodel.bus

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.vmyan.myantrip.data.booking.carRental.bus.BusListRepository

class BusListVMFactory (private val busListRepository: BusListRepository) : ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(BusListRepository ::class.java).newInstance(busListRepository)
    }

}