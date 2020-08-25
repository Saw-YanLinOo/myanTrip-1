package com.vmyan.myantrip.data.booking.carRental.hotel

import androidx.annotation.MainThread
import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.minbanyar.testbooking.AsyncTask.DeleteAllHotelWordsAsyncTask
import com.vmyan.myantrip.dao.RecentDao
import com.vmyan.myantrip.model.hotel.HotelRecentItem


class HotelRecentRepository(private val recentDao: RecentDao){
 val allItems:LiveData<List<HotelRecentItem>> =recentDao.getHotelRecentHotel()

@Suppress("RedundantSuspendModifier")
@WorkerThread
suspend fun insert(items :HotelRecentItem){
      recentDao.insert(items)
}
   @MainThread
    fun clearAll() {
        DeleteAllHotelWordsAsyncTask(recentDao).execute()
    }

}