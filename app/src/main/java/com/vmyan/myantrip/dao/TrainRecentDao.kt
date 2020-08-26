package com.vmyan.myantrip.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.vmyan.myantrip.model.flight.FlightRecentItem
import com.vmyan.myantrip.model.train.TrainRecentItem

@Dao
interface TrainRecentDao {
    @Query("SELECT * FROM  train_Recent_table ")
    fun getTrainRecentList() : LiveData<List<TrainRecentItem>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(items : TrainRecentItem)

    @Query("DELETE FROM train_Recent_table")
    fun clearAll()
}