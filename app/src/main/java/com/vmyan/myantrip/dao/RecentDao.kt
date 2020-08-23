package com.vmyan.myantrip.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.vmyan.myantrip.model.hotel.HotelRecentItem

@Dao
interface RecentDao{
    @Query("SELECT * FROM  recent_table ORDER BY lDate DESC")
        fun getHotelRecentHotel() : LiveData<List<HotelRecentItem>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
        fun insert(items : HotelRecentItem)

    @Query("DELETE FROM recent_table")
    fun clearAll()
}