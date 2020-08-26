package com.vmyan.myantrip.ui.viewmodel.train

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.vmyan.myantrip.data.booking.carRental.train.TrainRepository
import com.vmyan.myantrip.utils.Resource
import kotlinx.coroutines.Dispatchers

class TrainVM (private val trainRepository: TrainRepository) :ViewModel(){
    fun fetchTrainList() = liveData(Dispatchers.IO) {
        emit(Resource.Loading())
        try {
            val flightList = trainRepository.getAllTrainList()
            emit(flightList)
        } catch (e: Exception) {
            emit(Resource.Failure(e.message!!))
        }
    }
    val fetchTrainPromoImages = liveData(Dispatchers.IO) {
        emit(Resource.Loading())
        try {
            val promoSliderList = trainRepository.getTrainPromoImages()
            emit(promoSliderList)
        } catch (e: Exception) {
            emit(Resource.Failure(e.message!!))
        }
    }

}