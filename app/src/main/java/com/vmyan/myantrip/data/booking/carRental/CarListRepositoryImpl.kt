package com.vmyan.myantrip.data.booking.carRental

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.vmyan.myantrip.model.carRental.CarRentailsItem
import com.vmyan.myantrip.utils.Resource
import kotlinx.coroutines.tasks.await

class CarListRepositoryImpl  : CarListRepository {
    override suspend fun getCarRentailsList(): Resource<MutableList<CarRentailsItem>> {
        val carList = mutableListOf <CarRentailsItem>()
        val resultList= FirebaseFirestore.getInstance()
            .collection("/BookingCategories/3dI2TTyLoXw6pnWWghW1/CarRentals")
            .orderBy("PricePerDay", Query.Direction.ASCENDING)
            .get()
            .await()
        for (car in resultList){
            val carId = car.id
            val carName =car.getString("CarName")
            val currentLocation =car.getString("CurrentLocation")
            val noOfPeople  =car.getString("NoOfPeople")
            val noOfBag  =car.getString("NoOfBag")
            val carStatus =car.getString("CarStatus")
            val pricePerDay =car.getString("PricePerDay")
            val driverStatus  =car.getBoolean("DriverStatus")
            val carCompanyLogo =car.getString("CarCompanyLogo")
            val carImage =car.getString("CarImage")


            println(carId)
            println(carName)
            println(currentLocation)
            println(noOfPeople)
            println(noOfBag)
            println(carStatus)
            println(pricePerDay)
            println(driverStatus)
            println(carCompanyLogo)
            println(carImage)
            carList.add(
                CarRentailsItem(
                    carId,
                    carName!!,
                    currentLocation!!,
                    noOfPeople!!,
                    noOfBag!!,
                    carStatus!!,
                    pricePerDay!!,
                   driverStatus!!,
                    carCompanyLogo!!,
                    carImage!!

                )
            )
        }

        return Resource.Success(carList)

    }


}

