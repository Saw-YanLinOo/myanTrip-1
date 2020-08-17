package com.vmyan.myantrip.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.vmyan.myantrip.data.AddNewTripRepository
import com.vmyan.myantrip.data.UpComingTripRepository

class UpComingTripVMFactory (private val upComingTripRepository: UpComingTripRepository) : ViewModelProvider.NewInstanceFactory(){
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return UpComingTripViewModel(upComingTripRepository) as T
    }
}