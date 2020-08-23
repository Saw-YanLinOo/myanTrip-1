package com.vmyan.myantrip.ui.viewmodel.hotel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.vmyan.myantrip.data.booking.carRental.hotel.RoomRepository
import com.vmyan.myantrip.utils.Resource
import kotlinx.coroutines.Dispatchers

class RoomListVM(private val roomRepository : RoomRepository) : ViewModel(){
    fun fetchAllRoomList(id :String,cid: String) = liveData(Dispatchers.IO) {
        emit(Resource.Loading())
        try {
            val hotelList = roomRepository.getAllRoomList(id,cid)
            emit(hotelList)
        }catch (e: Exception){
            emit(Resource.Failure(e.message!!))
        }
    }

}