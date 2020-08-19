package com.vmyan.myantrip.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.vmyan.myantrip.data.ProfileRepository
import com.vmyan.myantrip.utils.Resource
import kotlinx.coroutines.Dispatchers
import java.lang.Exception

class ProfileViewModel(private val profileRepository: ProfileRepository) : ViewModel() {
    fun getUserProfile(user_id : String) = liveData(Dispatchers.IO) {
        emit(Resource.Loading())
        try {
            val blogLists = profileRepository.getProfile(user_id)
            emit(blogLists)
        }catch (e : Exception){
            emit(Resource.Failure(e.cause.toString()))
        }
    }

    fun updatedProfile(image : String) = liveData(Dispatchers.IO) {
        emit(Resource.Loading())
        try {
            val blogLists = profileRepository.updateProfilePhoto(image)
            emit(blogLists)
        }catch (e : Exception){
            emit(Resource.Failure(e.cause.toString()))
        }
    }
}