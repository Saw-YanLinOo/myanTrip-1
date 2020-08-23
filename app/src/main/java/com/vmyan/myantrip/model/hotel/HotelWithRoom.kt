package com.vmyan.myantrip.model.hotel

data class HotelWithRoom(
    var hotelList : HotelList,
    var roomList : MutableList<RoomList>
)