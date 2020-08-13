package com.vmyan.myantrip.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.vmyan.myantrip.data.PlaceByCategoryRepository
import com.vmyan.myantrip.data.SearchPlaceRepository
import com.vmyan.myantrip.utils.Resource
import kotlinx.coroutines.Dispatchers

class SearchPlaceViewModel (private val searchPlaceRepository: SearchPlaceRepository) : ViewModel() {

    fun fetchPlaceBySearch() = liveData(Dispatchers.IO) {
        emit(Resource.Loading())
        try {
            val placeList = searchPlaceRepository.getPlaceBySearch()
            emit(placeList)
        }catch (e: Exception){
            emit(Resource.Failure(e.cause!!.toString()))
        }
    }

}