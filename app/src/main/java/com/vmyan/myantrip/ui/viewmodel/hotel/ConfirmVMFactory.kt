package com.vmyan.myantrip.ui.viewmodel.hotel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.vmyan.myantrip.data.booking.carRental.hotel.ConfirmRepository

class ConfirmVMFactory (private val confirmRepository: ConfirmRepository) : ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(ConfirmRepository::class.java).newInstance(confirmRepository)
    }

}