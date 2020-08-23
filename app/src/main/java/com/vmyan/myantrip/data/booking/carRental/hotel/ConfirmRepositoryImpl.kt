package com.vmyan.myantrip.data.booking.carRental.hotel

import com.google.firebase.firestore.FieldPath
import com.google.firebase.firestore.FirebaseFirestore
import com.vmyan.myantrip.model.hotel.ConfirmHotel
import com.vmyan.myantrip.model.hotel.RoomList
import com.vmyan.myantrip.model.hotel.HotelList
import com.vmyan.myantrip.utils.Resource

import kotlinx.coroutines.tasks.await

class ConfirmRepositoryImpl :
    ConfirmRepository {
    override suspend fun getConfirmDetails(
        hotel_id: String,
        c_id: String,
        room_id: String
    ): Resource<MutableList<ConfirmHotel>> {
        val allRoomList = mutableListOf <ConfirmHotel>()

        val resultList= FirebaseFirestore.getInstance()

            .collection("/BookingCategories/"+c_id+"/HotelDetails")
            .whereEqualTo(FieldPath.documentId(),hotel_id)
            .get()
            .await()
        for(hotelData in resultList) {
            val id = hotelData.id
            val hotel_name = hotelData.getString("hotel_name")
            val hotel_address = hotelData.getString("hotel_address")
            val hotelRate = hotelData.getString("hotel_rate")
            val hotel_city = hotelData.getString("hotel_city")
            val hotel_country = hotelData.getString("hotel_country")
            val hotel_image = hotelData.getString("hotel_image")
            val miniRoomPrice = hotelData.getString("miniRoomPrice")
            val phoneNo = hotelData.getString("phoneNo")
            val isWifi = hotelData.getBoolean("isWifi")
            val isBreakfast = hotelData.getBoolean("isBreakfast")
            val isCarPack = hotelData.getBoolean("isCarPack")
            val isRestaurant = hotelData.getBoolean("isRestaurant")
            val isWaterPool = hotelData.getBoolean("isWaterPool")
            var roomGalleryHotel =hotelData.get("roomImages")

            val roomList = mutableListOf<RoomList>()
            val resultRoomList = FirebaseFirestore.getInstance()
                .collection("/BookingCategories/" + c_id + "/HotelDetails/" + hotel_id + "/room_list/" )
                .whereEqualTo(FieldPath.documentId(),room_id)
                .get()
                .await()
            for (roomAllList in resultRoomList) {

                val roomId = roomAllList.id
                val roomType = roomAllList.getString("room_type")
                val roomPrice = roomAllList.getString("room_price")
                val roomWifi = roomAllList.getBoolean("isRoomWifi")
                val roomAircon = roomAllList.getBoolean("isRoomAircon")
                val roomWithToilet = roomAllList.getBoolean("isRoomwithTiilet")
                allRoomList.add(
                    ConfirmHotel(
                        HotelList(
                            id,
                            hotel_name!!,
                            hotel_address!!,
                            hotel_city!!,
                            hotel_country!!,
                            hotelRate!!,
                            miniRoomPrice!!,
                            phoneNo!!,
                            hotel_image!!,
                            isWifi!!,
                            isBreakfast!!,
                            isCarPack!!,
                            isWaterPool!!,
                            isRestaurant!!,
                            roomGalleryHotel as ArrayList<String>

                        ),
                        RoomList(
                            roomId,
                            roomType!!,
                            roomPrice!!,
                            roomWifi!!,
                            roomAircon!!,
                            roomWithToilet!!
                        )
                    )
                )
            }
        }

        return Resource.Success(allRoomList)
    }

}