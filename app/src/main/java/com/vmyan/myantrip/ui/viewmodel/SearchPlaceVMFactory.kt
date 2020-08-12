package com.vmyan.myantrip.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.vmyan.myantrip.data.PlaceByCategoryRepository
import com.vmyan.myantrip.data.SearchPlaceRepository

class SearchPlaceVMFactory (private val searchPlaceRepository: SearchPlaceRepository) : ViewModelProvider.NewInstanceFactory(){
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return SearchPlaceViewModel(searchPlaceRepository) as T
    }
}