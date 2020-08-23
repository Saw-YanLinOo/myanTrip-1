package com.vmyan.myantrip.ui.viewmodel.flight

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.vmyan.myantrip.data.booking.carRental.flight.FlightListRepository

class FlightListVMFactory (private val flightListRepository: FlightListRepository) :ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(FlightListRepository ::class.java).newInstance(flightListRepository)
    }

}