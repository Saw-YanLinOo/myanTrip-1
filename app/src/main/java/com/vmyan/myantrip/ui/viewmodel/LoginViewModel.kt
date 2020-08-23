package com.vmyan.myantrip.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.vmyan.myantrip.data.LoginRepository
import com.vmyan.myantrip.utils.Resource
import kotlinx.coroutines.Dispatchers
import java.lang.Exception

class LoginViewModel(private val loginRepository: LoginRepository): ViewModel() {

    fun user(id : String) = liveData(Dispatchers.IO) {
        emit(Resource.Loading())
        try {
            val user = loginRepository.getCurrentUser(id)
            emit(user)
        }catch (e : Exception){
            emit(Resource.Failure(e.message.toString()))
        }
    }
    fun Login(emial:String, password:String) = liveData(Dispatchers.IO) {
        emit(Resource.Loading())
        try {
            val auth = loginRepository.signIn("${emial}","${password}")
            emit(auth)
        }catch (e:Exception){
            e.printStackTrace()
            emit(Resource.Failure(e.message.toString()))
        }
    }
}