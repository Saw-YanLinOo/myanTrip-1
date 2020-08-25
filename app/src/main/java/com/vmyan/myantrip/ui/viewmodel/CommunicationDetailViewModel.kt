package com.vmyan.myantrip.ui.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.vmyan.myantrip.data.CommunicationDetailRepo
import com.vmyan.myantrip.utils.Resource
import kotlinx.coroutines.Dispatchers

class CommunicationDetailViewModel(private var communicationDetailRepo: CommunicationDetailRepo) : ViewModel() {
    fun downloadFun(context: Context) = liveData(Dispatchers.IO) {
        emit(Resource.Loading())
        try {
            var result = communicationDetailRepo.getFileR(context)
            emit(result)
        }catch (e : java.lang.Exception){
            emit(Resource.Failure(e.message.toString()))
        }
    }
}