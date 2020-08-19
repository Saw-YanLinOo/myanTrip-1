package com.vmyan.myantrip.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.google.firebase.Timestamp
import com.vmyan.myantrip.data.AddPlanRepository
import com.vmyan.myantrip.utils.Resource
import kotlinx.coroutines.Dispatchers

class AddPlanViewModel (private val addPlanRepository: AddPlanRepository) : ViewModel() {

    fun addPlan(
        tripId: String,
        name: String,
        img: String,
        fromDate: Timestamp,
        toDate: Timestamp?,
        fromTime: String,
        toTime: String,
        fromState: String,
        toState: String,
        fromCity: String,
        toCity: String,
        fromAddress: String,
        toAddress: String,
        estimationCost: Int,
        confirmation: Boolean,
        type: String,
        description: String,
        details: String,
        viewType : String
    ) = liveData(Dispatchers.IO) {
        emit(Resource.Loading())
        try {
            val addPlan = addPlanRepository.addPlan(tripId, name, img, fromDate, toDate, fromTime, toTime, fromState, toState, fromCity, toCity, fromAddress, toAddress, estimationCost, confirmation, type, description, details, viewType)
            emit(addPlan)
        }catch (e: Exception){
            emit(Resource.Failure(e.cause!!.toString()))
        }
    }

}
