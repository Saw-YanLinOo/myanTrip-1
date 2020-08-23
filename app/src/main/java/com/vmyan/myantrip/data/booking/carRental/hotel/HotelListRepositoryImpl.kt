package com.vmyan.myantrip.data.booking.carRental.hotel

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.vmyan.myantrip.model.hotel.HotelList
import com.vmyan.myantrip.utils.Resource
import kotlinx.coroutines.tasks.await

class HotelListRepositoryImpl :
    HotelListRepository {
    override suspend fun getHotelList(): Resource<MutableList<HotelList>> {
        val hotelList = mutableListOf<HotelList>()
        val resultList = FirebaseFirestore.getInstance()
            .collection("/BookingCategories/0howcfkVUqShkvPlTZqg/HotelDetails").orderBy("hotel_name",Query.Direction.ASCENDING)
            .get()
            .await()
        for (document in resultList) {
            val hotel_id = document.id
            val hotel_name = document.getString("hotel_name")
            val hotel_address = document.getString("hotel_address")
             val hotelRate = document.getString("hotel_rate")
            val hotel_city = document.getString("hotel_city")
            val hotel_country = document.getString("hotel_country")
           val hotel_image = document.getString("hotel_image")
            val miniRoomPrice = document.getString("miniRoomPrice")
            val phoneNo = document.getString("phoneNo")
            val isWifi = document.getBoolean("isWifi")
            val isBreakfast = document.getBoolean("isBreakfast")
            val isCarPack = document.getBoolean("isCarPack")
            val isRestaurant = document.getBoolean("isRestaurant")
            val isWaterPool = document.getBoolean("isWaterPool")
            val roomImag=document.get("roomImages") as ArrayList<String>
            println(hotel_id)
            println(hotel_name)
            println(hotel_address)
            println(hotelRate)

            println(hotel_city)
            println(hotel_country)
            println(hotel_image)
            println(miniRoomPrice)

            println(phoneNo)
            println(isWifi)
            println(isBreakfast)
            println(isCarPack)
            println(isRestaurant)
            println(isWifi)
            println(isWaterPool)
            hotelList.add(
                HotelList(
                    hotel_id,
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
                    roomImag


                )
            )
        }

        return Resource.Success(hotelList)
    }

    override suspend fun getTopRatingHotelList(): Resource<MutableList<HotelList>> {
        val topRatingHotelList = mutableListOf<HotelList>()
        val resultList = FirebaseFirestore.getInstance()
            .collection("/BookingCategories/0howcfkVUqShkvPlTZqg/HotelDetails").orderBy("hotel_rate",Query.Direction.DESCENDING).limit(5)
            .get()
            .await()
        for (document in resultList) {
            val hotel_id = document.id
            val hotel_name = document.getString("hotel_name")
            val hotel_address = document.getString("hotel_address")
            val hotelRate = document.getString("hotel_rate")
            val hotel_city = document.getString("hotel_city")
            val hotel_country = document.getString("hotel_country")
            val hotel_image = document.getString("hotel_image")
            val miniRoomPrice = document.getString("miniRoomPrice")
            val phoneNo = document.getString("phoneNo")
            val isWifi = document.getBoolean("isWifi")
            val isBreakfast = document.getBoolean("isBreakfast")
            val isCarPack = document.getBoolean("isCarPack")
            val isRestaurant = document.getBoolean("isRestaurant")
            val isWaterPool = document.getBoolean("isWaterPool")
            val roomImag=document.get("roomImages") as ArrayList<String>


            topRatingHotelList.add(
                HotelList(
                    hotel_id,
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
                    roomImag

                )
            )
        }

        return Resource.Success(topRatingHotelList)
    }

    override suspend fun getLowestPriceHotelList(): Resource<MutableList<HotelList>> {
        val topRatingHotelList = mutableListOf<HotelList>()
        val resultList = FirebaseFirestore.getInstance()
            .collection("/BookingCategories/0howcfkVUqShkvPlTZqg/HotelDetails").orderBy("miniRoomPrice",Query.Direction.ASCENDING).limit(5)
            .get()
            .await()
        for (document in resultList) {
            val hotel_id = document.id
            val hotel_name = document.getString("hotel_name")
            val hotel_address = document.getString("hotel_address")
            val hotelRate = document.getString("hotel_rate")
            val hotel_city = document.getString("hotel_city")
            val hotel_country = document.getString("hotel_country")
            val hotel_image = document.getString("hotel_image")
            val miniRoomPrice = document.getString("miniRoomPrice")
            val phoneNo = document.getString("phoneNo")
            val isWifi = document.getBoolean("isWifi")
            val isBreakfast = document.getBoolean("isBreakfast")
            val isCarPack = document.getBoolean("isCarPack")
            val isRestaurant = document.getBoolean("isRestaurant")
            val isWaterPool = document.getBoolean("isWaterPool")
            val roomImag=document.get("roomImages") as ArrayList<String>


            topRatingHotelList.add(
                HotelList(
                    hotel_id,
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
                    roomImag

                )
            )
        }

        return Resource.Success(topRatingHotelList)
    }
    override suspend fun getHighestPriceHotelList(): Resource<MutableList<HotelList>> {
        val topRatingHotelList = mutableListOf<HotelList>()
        val resultList = FirebaseFirestore.getInstance()
            .collection("/BookingCategories/0howcfkVUqShkvPlTZqg/HotelDetails").orderBy("miniRoomPrice",Query.Direction.DESCENDING).limit(5)
            .get()
            .await()
        for (document in resultList) {
            val hotel_id = document.id
            val hotel_name = document.getString("hotel_name")
            val hotel_address = document.getString("hotel_address")
            val hotelRate = document.getString("hotel_rate")
            val hotel_city = document.getString("hotel_city")
            val hotel_country = document.getString("hotel_country")
            val hotel_image = document.getString("hotel_image")
            val miniRoomPrice = document.getString("miniRoomPrice")
            val phoneNo = document.getString("phoneNo")
            val isWifi = document.getBoolean("isWifi")
            val isBreakfast = document.getBoolean("isBreakfast")
            val isCarPack = document.getBoolean("isCarPack")
            val isRestaurant = document.getBoolean("isRestaurant")
            val isWaterPool = document.getBoolean("isWaterPool")
            val roomImag=document.get("roomImages") as ArrayList<String>


            topRatingHotelList.add(
                HotelList(
                    hotel_id,
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
                    roomImag

                )
            )
        }

        return Resource.Success(topRatingHotelList)
    }

}














