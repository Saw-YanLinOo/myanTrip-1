package com.vmyan.myantrip.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.vmyan.myantrip.data.AddNewTripRepository

class AddNewTripVMFactory (private val addNewTripRepository: AddNewTripRepository) : ViewModelProvider.NewInstanceFactory(){
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return AddNewTripViewModel(addNewTripRepository) as T
    }
}