package com.vmyan.myantrip.data

import androidx.lifecycle.MutableLiveData
import com.vmyan.myantrip.model.GetPost
import com.vmyan.myantrip.utils.Resource

interface ProfileRepository {
    suspend fun getProfile(user_id : String) : Resource<MutableList<GetPost>>
    suspend fun updateProfilePhoto(uri : String) : Resource<MutableLiveData<String>>
}