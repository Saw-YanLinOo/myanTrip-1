package com.vmyan.myantrip.data

import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.AuthResult
import com.vmyan.myantrip.model.User
import com.vmyan.myantrip.utils.Resource

interface RegisterRepository {

    suspend fun getCurrentUser(id:String):Resource<MutableLiveData<User>>
    suspend fun signUp(email:String,password: String) : Resource<MutableLiveData<AuthResult>>
    suspend fun addNewUser(user: User)
}