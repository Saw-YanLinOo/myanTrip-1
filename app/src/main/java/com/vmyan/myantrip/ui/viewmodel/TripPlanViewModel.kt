package com.vmyan.myantrip.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.vmyan.myantrip.data.TripPlanRepository
import com.vmyan.myantrip.utils.Resource
import kotlinx.coroutines.Dispatchers

class TripPlanViewModel (private val tripPlanRepository: TripPlanRepository) : ViewModel() {

    fun getTripPlan(
        tripId: String
    ) = liveData(Dispatchers.IO) {
        emit(Resource.Loading())
        try {
            val tripPlan = tripPlanRepository.getTripPlan(tripId)
            emit(tripPlan)
        }catch (e: Exception){
            emit(Resource.Failure(e.cause!!.toString()))
        }
    }

}
