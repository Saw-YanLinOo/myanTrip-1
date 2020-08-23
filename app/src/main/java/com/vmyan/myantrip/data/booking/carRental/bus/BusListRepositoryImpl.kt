package com.vmyan.myantrip.data.booking.carRental.bus

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.vmyan.myantrip.model.bus.BusListItem
import com.vmyan.myantrip.utils.Resource
import kotlinx.coroutines.tasks.await

class BusListRepositoryImpl : BusListRepository {
    override suspend fun getAllBusList(): Resource<MutableList<BusListItem>> {
        val busList = mutableListOf <BusListItem>()
        val resultList= FirebaseFirestore.getInstance()
            .collection("/BookingCategories/j7hFB47Mt4h0pq6nvKgb/BusDetails")
            .orderBy("BusTimeStart", Query.Direction.ASCENDING)
            .get()
            .await()
        for (bus in resultList){
            val busId = bus.id
            val busName =bus.getString("BusName")
            val busStatus =bus.getString("BusStatus")
            val busFrom  =bus.getString("BusFrom")
            val busTo  =bus.getString("BusTo")
            val busFromTo =bus.getString("BusFromTo")
            val busDeparture =bus.getString("BusDeparture")
            val busTimeStart =bus.getString("BusTimeStart")
            val busTimeStop  =bus.getString("BusTimeStop")
            val busPricePerSeat =bus.getString("BusPricePerSeat")
            val busTotalNoSeat =bus.getString("BusTotalNoSeat")
            val busImage =bus.getString("BusImage")


            println(busId)
            println(busName)
            println(busStatus)
            println(busFrom)
            println(busTo)
            println(busDeparture)
            println(busTimeStart)
            println(busTimeStop)
            println(busPricePerSeat)
            println(busTotalNoSeat)
            println(busImage)
            busList.add(
                BusListItem(
                    busId,
                    busName!!,
                    busStatus!!,
                    busFrom!!,
                    busTo!!,
                    busFromTo!!,
                    busDeparture!!,
                    busTimeStart!!,
                    busTimeStop!!,
                    busPricePerSeat!!,
                    busTotalNoSeat!!,
                    busImage!!

                )
            )
        }

        return Resource.Success(busList)
    }

}










