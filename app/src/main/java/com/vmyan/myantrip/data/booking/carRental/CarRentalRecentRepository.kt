package com.vmyan.myantrip.data.booking.carRental

import androidx.annotation.MainThread
import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.vmyan.myantrip.asyncTask.DeleteAllCarRentalWordAsynTask
import com.vmyan.myantrip.dao.CarRentalDao
import com.vmyan.myantrip.model.carRental.CarRentalRecentItem

class CarRentalRecentRepository (private val recentDao: CarRentalDao) {
    val allItems: LiveData<List<CarRentalRecentItem>> = recentDao.getCarRentalRecentHotel()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(items: CarRentalRecentItem) {
        recentDao.insert(items)
    }

    @MainThread
    fun clearAll() {
        DeleteAllCarRentalWordAsynTask(recentDao).execute()
    }
}