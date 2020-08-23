package com.vmyan.myantrip.data.booking.carRental.hotel

import com.vmyan.myantrip.model.hotel.HotelWithRoom
import com.vmyan.myantrip.utils.Resource

interface RoomRepository {
    suspend fun getAllRoomList(hotel_id :String,cid: String) : Resource<MutableList<HotelWithRoom>>
}