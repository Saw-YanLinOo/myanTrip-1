package com.vmyan.myantrip.model.hotel

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "recent_table")

data class HotelRecentItem(@PrimaryKey val cityImage : String ,
                           val townName: String,
                           val sDate: String,
                           val lDate : String  ,
                           val noOfGuests : String ,
                           val noOfRoom :String)