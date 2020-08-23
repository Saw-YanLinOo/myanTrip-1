package com.vmyan.myantrip.ui.viewmodel.hotel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.vmyan.myantrip.data.booking.carRental.hotel.RoomRepository

class RoomListVMFactory(private val roomRepository: RoomRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(RoomRepository::class.java).newInstance(roomRepository)
    }
}