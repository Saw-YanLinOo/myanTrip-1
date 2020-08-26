package com.vmyan.myantrip.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.google.firebase.Timestamp
import com.vmyan.myantrip.data.BookingTicketRepository
import com.vmyan.myantrip.utils.Resource
import kotlinx.coroutines.Dispatchers

class BookingTicketViewModel (private val bookingTicketRepository: BookingTicketRepository) : ViewModel() {

    fun addTicket(
        dateFrom: String,
        dateTo: String,
        totalCost: Long,
        tName: String,
        tTypeImage: String
    ) = liveData(Dispatchers.IO) {
        emit(Resource.Loading())

        try {
            val result = bookingTicketRepository.addTicket(dateFrom, dateTo, totalCost, tName, tTypeImage)
            emit(result)
        }catch (e: Exception){
            e.printStackTrace()
            emit(Resource.Failure(e.cause!!.toString()))
        }
    }

    fun preTicket() = liveData(Dispatchers.IO) {
        emit(Resource.Loading())

        try {
            val result = bookingTicketRepository.getPreTicketList()
            emit(result)
        }catch (e: Exception){
            e.printStackTrace()
            emit(Resource.Failure(e.cause!!.toString()))
        }
    }

    fun expTicket() = liveData(Dispatchers.IO) {
        emit(Resource.Loading())

        try {
            val result = bookingTicketRepository.getExpTicketList()
            emit(result)
        }catch (e: Exception){
            e.printStackTrace()
            emit(Resource.Failure(e.cause!!.toString()))
        }
    }



}