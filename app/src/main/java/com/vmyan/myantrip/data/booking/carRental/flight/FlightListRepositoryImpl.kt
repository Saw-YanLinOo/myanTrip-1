package com.vmyan.myantrip.data.booking.carRental.flight

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.vmyan.myantrip.model.flight.FlightListItem
import com.vmyan.myantrip.utils.Resource
import kotlinx.coroutines.tasks.await

class FlightListRepositoryImpl : FlightListRepository {
    override suspend fun getFlightList(): Resource<MutableList<FlightListItem>> {
        val flightList = mutableListOf <FlightListItem>()
        val resultList= FirebaseFirestore.getInstance()
            .collection("/BookingCategories/HDHRdyAsUBz6w4N1qnmZ/FlightDetails")
            .orderBy("FTimeStart",Query.Direction.ASCENDING)
            .get()
            .await()
        for (flight in resultList){
            val flightId = flight.id
            val flightName =flight.getString("FlightName")
            val flightFrom =flight.getString("FlightFrom")
            val flightTo =flight.getString("FlightTo")
            val flightDeparture =flight.getString("FlightDeparture")
            val fTimeStart =flight.getString("FTimeStart")
            val fTimeStop =flight.getString("FTimeStop")
            val flightPrice = flight.getDouble("FlightPrice")?.toLong()
            val flightClass =flight.getString("FlightClass")
            val fBaggageAllow =flight.getString("FBaggageAllow")

            val flightImage =flight.getString("FlightImage")
            println(flightId)
            println(flightName)
            println(flightFrom)
            println(flightTo)
            println(flightDeparture)
            println(fTimeStart)
            println(fTimeStop)
            println(flightPrice)
            println(flightClass)
            println(flightImage)
            flightList.add(
                FlightListItem(
                    flightId,
                    flightName!!,
                    flightFrom!!,
                    flightTo!!,
                    flightDeparture!!,
                    fTimeStart!!,
                    fTimeStop!!,
                    flightPrice!!,
                    flightClass!!,
                    fBaggageAllow!!,
                    flightImage!!
                )
            )
        }

        return Resource.Success(flightList)

    }

}









