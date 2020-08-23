package com.vmyan.myantrip.data.booking.carRental.hotel

import com.vmyan.myantrip.model.hotel.ConfirmHotel
import com.vmyan.myantrip.utils.Resource

interface ConfirmRepository {
    suspend fun getConfirmDetails(hotel_id :String, c_id :String, room_id :String) : Resource<MutableList<ConfirmHotel>>
}