package com.vmyan.myantrip.data.booking.carRental.flight

import androidx.annotation.MainThread
import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.vmyan.myantrip.asyncTask.DeleteAllWordsFlightAsyncTask
import com.vmyan.myantrip.dao.FlightRecentDao
import com.vmyan.myantrip.model.flight.FlightRecentItem


class FlightRecentRepository (private val flightRecentDao: FlightRecentDao){
    val allItems: LiveData<List<FlightRecentItem>> =flightRecentDao.getFlightRecentList()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(items : FlightRecentItem){
        flightRecentDao.insert(items)
    }
    @MainThread
    fun clearAll() {
        DeleteAllWordsFlightAsyncTask(flightRecentDao).execute()
    }
}