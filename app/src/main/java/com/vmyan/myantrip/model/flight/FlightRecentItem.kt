package com.vmyan.myantrip.model.flight

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "flight_recent_table")
data class FlightRecentItem(@PrimaryKey
                            val flightFrom :String ,
                            val flightTo : String ,
                            val flightDepartDate : String ,
                            val fAdultsCount : String,
                            val fChildCount : String ,
                            val fInfantCount : String)