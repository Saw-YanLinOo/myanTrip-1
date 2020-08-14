package com.vmyan.myantrip.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.google.firebase.Timestamp
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
            emit(Resource.Failure(e.cause!!.toString()))
        }
    }

    fun fetchNearByPlace(city: String) = liveData(Dispatchers.IO) {
        emit(Resource.Loading())
        try {
            val place = placeDetailsRepository.getNearByPlace(city)
            emit(place)
        }catch (e: Exception){
            emit(Resource.Failure(e.cause!!.toString()))
        }
    }

    fun addReview(cat_name: String, subcat_id: String, place_id: String, userid: String, username: String, userImg: String, rating: Float, desc: String, date: Timestamp) =
        liveData(Dispatchers.IO) {
            emit(Resource.Loading())
            try {
                val reviewResult = placeDetailsRepository.addReview(cat_name, subcat_id, place_id, userid, username, userImg, rating, desc, date)
                emit(reviewResult)
            }catch (e: Exception){
                emit(Resource.Failure(e.cause!!.toString()))
            }
        }
}
