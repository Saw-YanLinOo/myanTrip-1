package com.vmyan.myantrip.model.train

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName ="train_Recent_table")
data class TrainRecentItem(
    @PrimaryKey
    val trainFrom :String ,
    val trainTo : String ,
    val trainDepartDate : String ,
    val trainNoOfPeople : String,
)