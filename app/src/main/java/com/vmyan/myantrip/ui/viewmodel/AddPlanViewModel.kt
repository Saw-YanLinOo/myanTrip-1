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
        date: Timestamp,
        state: String,
        city: String,
        address: String,
        estimationCost: Int,
        confirmation: Boolean,
        type: String,
        description: String,
        details: String,
        viewType : Int,
        status: String
    ) = liveData(Dispatchers.IO) {
        emit(Resource.Loading())
        try {
            val addPlan = addPlanRepository.addPlan(tripId, name, img, date, state, city, address, estimationCost, confirmation, type, description, details, viewType, status)
            emit(addPlan)
        }catch (e: Exception){
            emit(Resource.Failure(e.cause!!.toString()))
        }
    }

}
