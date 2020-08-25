package com.vmyan.myantrip.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.vmyan.myantrip.model.carRental.CarRentalRecentItem
@Dao
interface CarRentalDao {
    @Query("SELECT * FROM carRental_Recent_table ORDER BY lDate DESC")
    fun getCarRentalRecentHotel() : LiveData<List<CarRentalRecentItem>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(items : CarRentalRecentItem)

    @Query("DELETE FROM carRental_Recent_table")
    fun clearAll()
}