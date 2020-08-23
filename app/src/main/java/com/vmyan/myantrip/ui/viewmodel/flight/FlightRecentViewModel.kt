package com.vmyan.myantrip.ui.viewmodel.flight

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.vmyan.myantrip.data.booking.carRental.flight.FlightRecentRepository
import com.vmyan.myantrip.database.FlightRecentDB
import com.vmyan.myantrip.model.flight.FlightRecentItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FlightRecentViewModel(application: Application): AndroidViewModel(application) {
    private val repository: FlightRecentRepository
    val allItems: LiveData<List<FlightRecentItem>>

    init {
        val recentsDao= FlightRecentDB.getDatabase(application,viewModelScope).flightRecentDao()
        repository= FlightRecentRepository(recentsDao)
        allItems=repository.allItems
    }
    fun insert(items: FlightRecentItem) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(items)
    }
    fun clearAll(){
        repository.clearAll()

    }
}
