package com.vmyan.myantrip.data.booking.carRental.flight


import com.vmyan.myantrip.model.flight.FlightListItem
import com.vmyan.myantrip.utils.Resource

interface FlightListRepository {
        suspend fun getFlightList () : Resource<MutableList<FlightListItem>>
}