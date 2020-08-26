package com.vmyan.myantrip.ui.viewmodel.train

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.vmyan.myantrip.data.booking.carRental.CarRentalRecentRepository
import com.vmyan.myantrip.data.booking.carRental.train.TrainRecentRepository
import com.vmyan.myantrip.database.CarRecentDB
import com.vmyan.myantrip.database.TrainRecnetDB
import com.vmyan.myantrip.model.carRental.CarRentalRecentItem
import com.vmyan.myantrip.model.train.TrainRecentItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TrainRecentViewModel (application: Application): AndroidViewModel(application) {
    private val repository: TrainRecentRepository
    val allItems: LiveData<List<TrainRecentItem>>

    init {
        val recentsDao = TrainRecnetDB.getDatabase(application, viewModelScope).trainRecentDao()
        repository = TrainRecentRepository(recentsDao)
        allItems = repository.allItems
    }

    fun insert(items: TrainRecentItem) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(items)
    }

    fun clearAll() {
        repository.clearAll()

    }
}