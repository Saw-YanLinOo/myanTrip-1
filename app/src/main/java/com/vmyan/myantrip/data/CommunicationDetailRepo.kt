package com.vmyan.myantrip.data

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.vmyan.myantrip.utils.Resource

interface CommunicationDetailRepo {
    suspend fun getFileR(context: Context) : Resource<MutableLiveData<String>>
}