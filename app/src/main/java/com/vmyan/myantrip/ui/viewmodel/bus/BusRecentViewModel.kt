package com.vmyan.myantrip.ui.viewmodel.bus

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.vmyan.myantrip.data.booking.carRental.bus.BusRecentRepository
import com.vmyan.myantrip.data.booking.carRental.hotel.HotelRecentRepository
import com.vmyan.myantrip.database.BusRecentDB
import com.vmyan.myantrip.database.HotelRecentDB
import com.vmyan.myantrip.model.bus.BusRecentItem
import com.vmyan.myantrip.model.hotel.HotelRecentItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class BusRecentViewModel (application: Application): AndroidViewModel(application) {
    private val repository: BusRecentRepository
    val allItems: LiveData<List<BusRecentItem>>

    init {
        val recentsDao = BusRecentDB.getDatabase(application, viewModelScope).busRecentDao()
        repository = BusRecentRepository(recentsDao)
        allItems = repository.allItems
    }

    fun insert(items: BusRecentItem) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(items)
    }

    fun clearAll() {
        repository.clearAll()
    }
}