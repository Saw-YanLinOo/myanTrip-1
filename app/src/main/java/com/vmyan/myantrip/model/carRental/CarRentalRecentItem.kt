package com.vmyan.myantrip.model.carRental

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName ="carRental_Recent_table")
data class CarRentalRecentItem(
    @PrimaryKey val cityImage : String,
    val townName: String,
    val sDate: String,
    val lDate : String,
    val noOfPeople : String
)