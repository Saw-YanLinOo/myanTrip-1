package com.vmyan.myantrip.data.booking.carRental.bus

import androidx.annotation.MainThread
import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.vmyan.myantrip.asyncTask.DeleteAllBusWordsAsyncTask
import com.vmyan.myantrip.dao.BusRecentDao
import com.vmyan.myantrip.model.bus.BusRecentItem

class BusRecentRepository (private val busRecentDao: BusRecentDao){
    val allItems: LiveData<List<BusRecentItem>> =busRecentDao.getBusRecentList()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(items : BusRecentItem){
        busRecentDao.insert(items)
    }
    @MainThread
    fun clearAll() {
        DeleteAllBusWordsAsyncTask(busRecentDao).execute()
    }
}