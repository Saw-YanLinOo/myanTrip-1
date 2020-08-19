package com.vmyan.myantrip.ui.viewmodel

import androidx.lifecycle.liveData
import com.google.firebase.Timestamp
import com.vmyan.myantrip.data.UploadPostRepository
import com.vmyan.myantrip.model.Posts
import com.vmyan.myantrip.utils.Resource
import kotlinx.coroutines.Dispatchers
import java.lang.Exception

class UploadViewModel(private val uploadPostRepository: UploadPostRepository) {
    fun setPost(description:String,imageLists : ArrayList<String>) = liveData(Dispatchers.IO) {
        emit(Resource.Loading())
        try {
            println("description===>"+description)
            println("imageList===>"+imageLists.toString())
            val blogLists = uploadPostRepository.setPost(Posts("","",description,imageLists,"0","0", Timestamp.now()))
            emit(blogLists)
        }catch (e : Exception){
            emit(Resource.Failure(e.cause.toString()))
        }
    }

    fun uploadPhoto(imageList : ArrayList<String>) = liveData(Dispatchers.IO) {
        emit(Resource.Loading())
        try {
            println("imageList===>"+imageList.toString())
            val blogLists = uploadPostRepository.uploadPhotos(imageList)
            emit(blogLists)
        }catch (e : Exception){
            emit(Resource.Failure(e.cause.toString()))
        }
    }
}