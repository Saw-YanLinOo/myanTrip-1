package com.vmyan.myantrip.data.booking.carRental

import com.vmyan.myantrip.model.carRental.CarPromoImages
import com.vmyan.myantrip.model.carRental.CarRentailsItem
import com.vmyan.myantrip.utils.Resource


interface CarListRepository {
    suspend fun getCarRentailsList() : Resource<MutableList<CarRentailsItem>>
    suspend fun getCarRentailsPromoImageList() : Resource<MutableList<CarPromoImages>>

}