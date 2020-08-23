package com.vmyan.myantrip.ui.viewmodel

import androidx.lifecycle.liveData
import com.vmyan.myantrip.data.RegisterRepository
import com.vmyan.myantrip.model.User
import com.vmyan.myantrip.utils.Resource
import kotlinx.coroutines.Dispatchers
import java.lang.Exception

class RegisterViewModel(private val registerRepository: RegisterRepository) {
    fun user(id : String) = liveData(Dispatchers.IO) {
        emit(Resource.Loading())
        try {
            val user = registerRepository.getCurrentUser(id)
            emit(user)
        }catch (e : Exception){
            emit(Resource.Failure(e.message.toString()))
        }
    }
    fun SignUp(name:String, emial:String, ph: String, password:String) = liveData(Dispatchers.IO) {
        emit(Resource.Loading())
        try {
            val auth = registerRepository.signUp("${emial}","${password}")
            val result = registerRepository.addNewUser(User("",ph,"${emial}","${name}","0","0","0","0"))
            emit(auth)
        }catch (e: Exception){
            e.printStackTrace()
            emit(Resource.Failure(e.message.toString()))
        }
    }
}