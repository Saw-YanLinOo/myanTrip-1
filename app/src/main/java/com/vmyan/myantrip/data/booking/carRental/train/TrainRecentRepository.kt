package com.vmyan.myantrip.data.booking.carRental.train

import androidx.annotation.MainThread
import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.vmyan.myantrip.asyncTask.DeleteAllTrainWordAsyncTask
import com.vmyan.myantrip.dao.TrainRecentDao
import com.vmyan.myantrip.model.train.TrainRecentItem

class TrainRecentRepository (private val recentDao: TrainRecentDao) {
    val allItems: LiveData<List<TrainRecentItem>> = recentDao.getTrainRecentList()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(items: TrainRecentItem) {
        recentDao.insert(items)
    }

    @MainThread
    fun clearAll() {
        DeleteAllTrainWordAsyncTask(recentDao).execute()
    }
}