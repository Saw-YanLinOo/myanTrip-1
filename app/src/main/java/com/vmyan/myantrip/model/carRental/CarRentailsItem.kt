package com.vmyan.myantrip.model.carRental

import com.google.firebase.firestore.DocumentId

data class CarRentailsItem(
    @DocumentId
    val carId : String,
    val carName :String,
    val currentLocation :String,
    val noOfPeople :String,
    val noOfBag :String,
    val carStatus :String,
    val pricePerDay :String,
   val driverStatus :Boolean,
    val carCompanyLogo :String,
    val carImage :String



)



 //number of  people inclued in one car






