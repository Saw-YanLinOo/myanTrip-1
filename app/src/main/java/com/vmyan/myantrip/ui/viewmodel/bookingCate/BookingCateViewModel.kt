package com.vmyan.myantrip.ui.viewmodel.bookingCate

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.vmyan.myantrip.data.booking.carRental.bookingCate.BookingCategoriesRepository
import com.vmyan.myantrip.utils.Resource
import kotlinx.coroutines.Dispatchers

class BookingCateViewModel(private val bookingCategoriesRepository: BookingCategoriesRepository) : ViewModel() {
    val fetchBookingCateList = liveData(Dispatchers.IO) {
        emit(Resource.Loading())
        try {
            val bookingCateList = bookingCategoriesRepository.getBookingCategories()
            emit(bookingCateList)
        } catch (e: Exception) {
            emit(Resource.Failure(e.message!!))
        }
    }

    val fetchPromoList = liveData(Dispatchers.IO) {
        emit(Resource.Loading())
        try {
            val promoSliderList = bookingCategoriesRepository.getPromoSliderImage()
            emit(promoSliderList)
        } catch (e: Exception) {
            emit(Resource.Failure(e.message!!))
        }
    }
    fun fetchCityList() = liveData(Dispatchers.IO) {
        emit(Resource.Loading())
        try {
            val cityList = bookingCategoriesRepository.getCityList()
            emit(cityList)
        } catch (e: Exception) {
            emit(Resource.Failure(e.message!!))
        }
    }
}