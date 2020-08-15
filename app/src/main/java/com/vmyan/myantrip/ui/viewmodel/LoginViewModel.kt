package com.vmyan.myantrip.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.vmyan.myantrip.data.LoginRepository
import com.vmyan.myantrip.model.User
import com.vmyan.myantrip.model.UserAccountSetting
import com.vmyan.myantrip.utils.Resource
import kotlinx.coroutines.Dispatchers
import java.lang.Exception
import kotlin.math.log

class LoginViewModel(private val loginRepository: LoginRepository): ViewModel() {

    val user = liveData(Dispatchers.IO) {
        emit(Resource.Loading())
        try {
            val user = loginRepository.getCurrentUser()
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
    fun SignUp(emial:String, password:String) = liveData(Dispatchers.IO) {
        emit(Resource.Loading())
        try {
            val auth = loginRepository.signUp("${emial}","${password}")
            val result = loginRepository.addNewUser(User("","","${emial}","",""))
            val mresult= loginRepository
                    .addUserAccountSetting(UserAccountSetting(
                        "",
                        0,
                        0,
                        0,
                        "",
                        "",
                        ""
                    ))
            emit(auth)
        }catch (e:Exception){
            e.printStackTrace()
            emit(Resource.Failure(e.message.toString()))
        }
    }
}