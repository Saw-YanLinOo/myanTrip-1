package com.vmyan.myantrip.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.vmyan.myantrip.data.PlaceDetailsRepository
import com.vmyan.myantrip.utils.Resource
import kotlinx.coroutines.Dispatchers

class PlaceDetailsViewModel (private val placeDetailsRepository: PlaceDetailsRepository) : ViewModel() {
    fun fetchPlace(id: String) = liveData(Dispatchers.IO) {
        emit(Resource.Loading())
        try {
            val place = placeDetailsRepository.getPlaceDetails(id)
            emit(place)
        }catch (e: Exception){
            emit(Resource.Failure(e.cause!!))
        }
    }
}
