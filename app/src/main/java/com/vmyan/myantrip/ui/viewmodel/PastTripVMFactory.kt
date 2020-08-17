package com.vmyan.myantrip.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.vmyan.myantrip.data.AddNewTripRepository
import com.vmyan.myantrip.data.PastTripRepository
import com.vmyan.myantrip.data.UpComingTripRepository

class PastTripVMFactory (private val pastTripRepository: PastTripRepository) : ViewModelProvider.NewInstanceFactory(){
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return PastTripViewModel(pastTripRepository) as T
    }
}