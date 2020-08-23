package com.minbanyar.testbooking.viewModel.hotel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.vmyan.myantrip.data.booking.carRental.hotel.HotelListRepository


class HotelListVMFactory(private val hotelListRepository: HotelListRepository) : ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(HotelListRepository::class.java).newInstance(hotelListRepository)
    }
}