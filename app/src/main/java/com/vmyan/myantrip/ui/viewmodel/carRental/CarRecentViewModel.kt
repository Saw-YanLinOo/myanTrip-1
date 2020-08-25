package com.vmyan.myantrip.ui.viewmodel.carRental

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.vmyan.myantrip.data.booking.carRental.CarRentalRecentRepository
import com.vmyan.myantrip.database.CarRecentDB
import com.vmyan.myantrip.model.carRental.CarRentalRecentItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CarRecentViewModel (application: Application): AndroidViewModel(application){
    private val repository: CarRentalRecentRepository
    val allItems: LiveData<List<CarRentalRecentItem>>

    init {
        val recentsDao= CarRecentDB.getDatabase(application,viewModelScope).recentDao()
        repository= CarRentalRecentRepository(recentsDao)
        allItems=repository.allItems
    }
    fun insert(items: CarRentalRecentItem) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(items)
    }
    fun clearAll(){
        repository.clearAll()

    }

}