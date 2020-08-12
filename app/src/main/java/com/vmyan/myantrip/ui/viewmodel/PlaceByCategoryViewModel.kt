package com.vmyan.myantrip.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.vmyan.myantrip.data.PlaceByCategoryRepository
import com.vmyan.myantrip.utils.Resource
import kotlinx.coroutines.Dispatchers

class PlaceByCategoryViewModel (private val placeByCategoryRepository: PlaceByCategoryRepository) : ViewModel() {

    fun fetchPlaceByCategory(id: String) = liveData(Dispatchers.IO) {
        emit(Resource.Loading())
        try {
            val placeList = placeByCategoryRepository.getPlaceByCategory(id)
            emit(placeList)
        }catch (e: Exception){
            emit(Resource.Failure(e.cause!!))
        }
    }

}