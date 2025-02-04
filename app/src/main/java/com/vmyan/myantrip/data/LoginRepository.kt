package com.vmyan.myantrip.data

import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseUser
import com.vmyan.myantrip.model.User
import com.vmyan.myantrip.model.UserAccountSetting
import com.vmyan.myantrip.utils.Resource

interface LoginRepository {
    suspend fun signIn(email: String, password: String): Resource<MutableLiveData<AuthResult>>

    suspend fun getCurrentUser(id:String):Resource<MutableLiveData<User>>
}