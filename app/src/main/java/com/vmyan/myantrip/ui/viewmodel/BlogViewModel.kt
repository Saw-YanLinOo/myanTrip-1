package com.vmyan.myantrip.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.vmyan.myantrip.data.BlogRepository
import com.vmyan.myantrip.utils.Resource
import kotlinx.coroutines.Dispatchers
import java.lang.Exception

class BlogViewModel (private val blogRepository: BlogRepository): ViewModel(){

    fun fetchAllPost() = liveData(Dispatchers.IO) {
        emit(Resource.Loading())
        try {
            val blogLists = blogRepository.getAllPost()
            emit(blogLists)
        }catch (e : Exception){
            emit(Resource.Failure(e.cause.toString()))
        }
    }

    fun getAllUser() = liveData(Dispatchers.IO) {
        emit(Resource.Loading())
        try {
            val blogLists = blogRepository.getAllUser()
            emit(blogLists)
        }catch (e : Exception){
            emit(Resource.Failure(e.cause.toString()))
        }
    }
}