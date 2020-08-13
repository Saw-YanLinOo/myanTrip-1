package com.vmyan.myantrip.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.vmyan.myantrip.data.HomeRepository
import com.vmyan.myantrip.utils.Resource
import kotlinx.coroutines.Dispatchers

class HomeViewModel (private val homeRepository: HomeRepository) : ViewModel() {


    val fetchPlaceCategoryList = liveData(Dispatchers.IO) {
        emit(Resource.Loading())
        try {
            val placeCategoryList = homeRepository.getPlaceCategory()
            emit(placeCategoryList)
        }catch (e: Exception){
            emit(Resource.Failure(e.cause!!.toString()))
        }
    }

    val fetchSubPlaceCategoryList = liveData(Dispatchers.IO) {
        emit(Resource.Loading())
        try {
            val subPlaceCategoryList = homeRepository.getSubPlaceCategory()
            emit(subPlaceCategoryList)
        }catch (e: Exception){
            emit(Resource.Failure(e.cause!!.toString()))
        }

    }


    fun fetchPlaceBySubCategory(id: String) = liveData(Dispatchers.IO) {
        emit(Resource.Loading())
        try {
            val placeList = homeRepository.getPlaceBySubCategory(id)
            emit(placeList)
        }catch (e: Exception){
            emit(Resource.Failure(e.cause!!.toString()))
        }
    }

}