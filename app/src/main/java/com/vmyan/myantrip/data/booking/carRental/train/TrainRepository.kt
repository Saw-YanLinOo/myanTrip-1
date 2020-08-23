package com.vmyan.myantrip.data.booking.carRental.train

import com.vmyan.myantrip.model.train.TrainListItem
import com.vmyan.myantrip.utils.Resource

interface TrainRepository {
    suspend fun  getAllTrainList () : Resource<MutableList<TrainListItem>>
}