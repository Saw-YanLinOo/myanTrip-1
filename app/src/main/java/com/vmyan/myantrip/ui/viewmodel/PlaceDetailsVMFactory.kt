package com.vmyan.myantrip.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.vmyan.myantrip.data.PlaceByCategoryRepository
import com.vmyan.myantrip.data.PlaceDetailsRepository

class PlaceDetailsVMFactory (private val placeDetailsRepository: PlaceDetailsRepository) : ViewModelProvider.NewInstanceFactory(){
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return PlaceDetailsViewModel(placeDetailsRepository) as T
    }
}