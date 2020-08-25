package com.vmyan.myantrip.model.bus

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "bus_recent_table")
data class BusRecentItem(
    @PrimaryKey
    val busFrom :String ,
    val busTo : String ,
    val busDepartDate : String ,
)