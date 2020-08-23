package com.vmyan.myantrip.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.vmyan.myantrip.model.flight.FlightRecentItem


@Dao
interface FlightRecentDao {
    @Query("SELECT * FROM  flight_recent_table ")
    fun getFlightRecentList() : LiveData<List<FlightRecentItem>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(items : FlightRecentItem)

    @Query("DELETE FROM flight_recent_table")
    fun clearAll()
}