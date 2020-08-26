package com.vmyan.myantrip.data.booking.carRental.hotel

import com.google.firebase.firestore.FieldPath
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.vmyan.myantrip.model.hotel.HotelList
import com.vmyan.myantrip.model.hotel.HotelWithRoom
import com.vmyan.myantrip.model.hotel.RoomList
import com.vmyan.myantrip.utils.Resource
import kotlinx.coroutines.tasks.await

class RoomRepositoryImpl : RoomRepository {
    override suspend fun getAllRoomList(hotel_id : String,cid: String): Resource<MutableList<HotelWithRoom>> {
        val allRoomList = mutableListOf <HotelWithRoom>()
        val resultList= FirebaseFirestore.getInstance()
            .collection("/BookingCategories/"+cid+"/HotelDetails")
            .whereEqualTo(FieldPath.documentId(),hotel_id)
            .get()
            .await()
        for(hotelData in resultList){
            val id = hotelData.id
            val hotel_name = hotelData.getString("hotel_name")
            val hotel_address = hotelData.getString("hotel_address")
            val hotelRate = hotelData.getString("hotel_rate")
            val hotel_city = hotelData.getString("hotel_city")
            val hotel_country = hotelData.getString("hotel_country")
            val hotel_image = hotelData.getString("hotel_image")
            val miniRoomPrice = hotelData.getDouble("miniRoomPrice")?.toLong()
            val phoneNo = hotelData.getString("phoneNo")
            val isWifi = hotelData.getBoolean("isWifi")
            val isBreakfast = hotelData.getBoolean("isBreakfast")
            val isCarPack = hotelData.getBoolean("isCarPack")
            val isRestaurant = hotelData.getBoolean("isRestaurant")
            val isWaterPool = hotelData.getBoolean("isWaterPool")
            val roomImages =hotelData.get("roomImages") as ArrayList<String>
            println(roomImages)
            val roomList= mutableListOf<RoomList>()
            val resultRoomList=FirebaseFirestore.getInstance()
                .collection("/BookingCategories/"+cid+"/HotelDetails/"+hotel_id+"/room_list")
                .orderBy("room_type",Query.Direction.ASCENDING)
                .get()
                .await()
            for (roomAllList in resultRoomList){
                val roomId = roomAllList.id
                val roomType = roomAllList.getString("room_type")
                val roomPrice = roomAllList.getDouble("room_price")?.toLong()
                val roomWifi = roomAllList.getBoolean("isRoomWifi")
                val roomAircon =roomAllList.getBoolean("isRoomAircon")
                val roomWithToilet=roomAllList.getBoolean("isRoomwithTiilet")
                roomList.add(RoomList(roomId,roomType!!, roomPrice!!,roomWifi!!,roomAircon!!,roomWithToilet!!))
            }
            allRoomList.add(
                HotelWithRoom(
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
                    roomImages

                ),
                roomList)
            )
        }
        return Resource.Success(allRoomList)
    }


}