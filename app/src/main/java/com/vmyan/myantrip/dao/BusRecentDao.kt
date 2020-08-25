package com.vmyan.myantrip.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.vmyan.myantrip.model.bus.BusRecentItem
import com.vmyan.myantrip.model.flight.FlightRecentItem

@Dao
interface BusRecentDao {
    @Query("SELECT * FROM  bus_recent_table ORDER BY busDepartDate DESC ")
    fun getBusRecentList() : LiveData<List<BusRecentItem>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(items : BusRecentItem)

    @Query("DELETE FROM bus_recent_table")
    fun clearAll()
}