package com.vmyan.myantrip.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.vmyan.myantrip.data.PlaceByCategoryRepository

class PlaceByCategoryVMFactory (private val placeByCategoryRepository: PlaceByCategoryRepository) : ViewModelProvider.NewInstanceFactory(){
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return PlaceByCategoryViewModel(placeByCategoryRepository) as T
    }
}