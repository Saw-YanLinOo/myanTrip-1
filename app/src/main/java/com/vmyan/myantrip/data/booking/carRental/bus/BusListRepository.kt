package com.vmyan.myantrip.data.booking.carRental.bus

import com.vmyan.myantrip.model.bus.BusListItem
import com.vmyan.myantrip.utils.Resource


interface BusListRepository {
    suspend fun  getAllBusList () : Resource<MutableList<BusListItem>>
}