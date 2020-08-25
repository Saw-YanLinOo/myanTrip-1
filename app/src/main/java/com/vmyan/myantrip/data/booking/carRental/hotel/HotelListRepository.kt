package com.vmyan.myantrip.data.booking.carRental.hotel

import com.vmyan.myantrip.model.hotel.HotelList
import com.vmyan.myantrip.model.hotel.HotelPromoImages
import com.vmyan.myantrip.utils.Resource


interface HotelListRepository {
    suspend fun getHotelList() : Resource<MutableList<HotelList>>
    suspend fun getTopRatingHotelList() : Resource<MutableList<HotelList>>
    suspend fun getLowestPriceHotelList() : Resource<MutableList<HotelList>>
    suspend fun getHighestPriceHotelList() : Resource<MutableList<HotelList>>
    suspend fun getHotelPromoImages() : Resource<MutableList<HotelPromoImages>>
}