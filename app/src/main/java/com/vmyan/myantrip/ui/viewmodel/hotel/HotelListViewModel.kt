package com.vmyan.myantrip.ui.viewmodel.hotel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.vmyan.myantrip.data.booking.carRental.hotel.HotelListRepository
import com.vmyan.myantrip.utils.Resource
import kotlinx.coroutines.Dispatchers

class HotelListViewModel (private val hotelListRepository: HotelListRepository) : ViewModel(){
    val fetchHotelList = liveData(Dispatchers.IO) {
        emit(Resource.Loading())
        try {
            val hotelList = hotelListRepository.getHotelList()
            emit(hotelList)
        }catch (e: Exception){
            emit(Resource.Failure(e.message!!))
        }
    }
    val fetchTopratingHotelList = liveData(Dispatchers.IO) {
        emit(Resource.Loading())
        try {
            val hotelList = hotelListRepository.getTopRatingHotelList()
            emit(hotelList)
        }catch (e: Exception){
            emit(Resource.Failure(e.message.toString()))
        }
    }
    val fetchLowestPriceHotelList = liveData(Dispatchers.IO) {
        emit(Resource.Loading())
        try {
            val hotelList = hotelListRepository.getLowestPriceHotelList()
            emit(hotelList)
        }catch (e: Exception){
            emit(Resource.Failure(e.message!!))
        }
    }
    val fetchHighestPriceHotelList = liveData(Dispatchers.IO) {
        emit(Resource.Loading())
        try {
            val hotelList = hotelListRepository.getHighestPriceHotelList()
            emit(hotelList)
        }catch (e: Exception){
            emit(Resource.Failure(e.message!!))
        }
    }
    val fetchHotelPromoImages = liveData(Dispatchers.IO) {
        emit(Resource.Loading())
        try {
            val promoSliderList = hotelListRepository.getHotelPromoImages()
            emit(promoSliderList)
        } catch (e: Exception) {
            emit(Resource.Failure(e.message!!))
        }
    }

}