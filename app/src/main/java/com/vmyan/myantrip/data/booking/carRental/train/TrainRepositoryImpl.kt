package com.vmyan.myantrip.data.booking.carRental.train

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.vmyan.myantrip.model.train.TrainListItem
import com.vmyan.myantrip.utils.Resource
import kotlinx.coroutines.tasks.await

class TrainRepositoryImpl : TrainRepository {
    override suspend fun getAllTrainList(): Resource<MutableList<TrainListItem>> {
        val trainList = mutableListOf <TrainListItem>()
        val resultList= FirebaseFirestore.getInstance()
            .collection("/BookingCategories/yTtXLW0pKedAEXBvlder/TrainDetails")
            .orderBy("TrainTimeStart", Query.Direction.ASCENDING)
            .get()
            .await()
        for (train in resultList){
            val trainId = train.id
            val tranNo =train.getString("TrainNo")
            val trainFrom =train.getString("TrainFrom")
            val trainTo  =train.getString("TrainTo")
            val trainFromTo =train.getString("TrainFromTo")
            val trainDeparture  =train.getString("TrainDeparture")
            val trainTimeStart =train.getString("TrainTimeStart")
            val trainTimeStop  =train.getString("TrainTimeStop")
            val trainStatus =train.getString("TrainStatus")
            val trainImage =train.getString("TrainImage")
            val trainPerSeatPrice = train.getDouble("TrainPerSeatPrice")?.toLong()

            println(trainId)
            println(tranNo)
            println(trainFrom)
            println(trainTo)
            println(trainDeparture)
            println(trainTimeStart)
            println(trainTimeStop)
            println(trainStatus)
            println(trainImage)
            println(trainPerSeatPrice)


            trainList.add(
                TrainListItem(
                    trainId,
                    tranNo!!,
                    trainFrom!!,
                    trainTo!!,
                    trainFromTo!!,
                    trainDeparture!!,
                    trainTimeStart!!,
                    trainTimeStop!!,
                    trainStatus!!,
                    trainImage!!,
                    trainPerSeatPrice!!

                )
            )
        }

        return Resource.Success(trainList)
    }

}