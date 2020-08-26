package com.vmyan.myantrip.data.booking.carRental.train

import com.vmyan.myantrip.model.hotel.HotelPromoImages
import com.vmyan.myantrip.model.train.TrainListItem
import com.vmyan.myantrip.model.train.TrainPromoImages
import com.vmyan.myantrip.utils.Resource

interface TrainRepository {
    suspend fun  getAllTrainList () : Resource<MutableList<TrainListItem>>
    suspend fun getTrainPromoImages() : Resource<MutableList<TrainPromoImages>>
}