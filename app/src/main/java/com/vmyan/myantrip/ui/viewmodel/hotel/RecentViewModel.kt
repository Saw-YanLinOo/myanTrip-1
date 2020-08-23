package com.vmyan.myantrip.ui.viewmodel.hotel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.vmyan.myantrip.database.HotelRecentDB
import com.vmyan.myantrip.data.booking.carRental.hotel.HotelRecentRepository
import com.vmyan.myantrip.model.hotel.HotelRecentItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RecentViewModel(application: Application):AndroidViewModel(application){
        private val repository: HotelRecentRepository
        val allItems: LiveData<List<HotelRecentItem>>

init {
        val recentsDao= HotelRecentDB.getDatabase(application,viewModelScope).recentDao()
        repository= HotelRecentRepository(recentsDao)
    allItems=repository.allItems
        }
    fun insert(items: HotelRecentItem) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(items)
    }
     fun clearAll(){
         repository.clearAll()

    }

}