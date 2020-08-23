package com.vmyan.myantrip.ui.viewmodel.train


import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.vmyan.myantrip.data.booking.carRental.train.TrainRepository

class TrainVMFactory (private val trainRepository: TrainRepository) : ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(TrainRepository::class.java).newInstance(trainRepository)
    }

}