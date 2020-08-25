package com.vmyan.myantrip.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.vmyan.myantrip.data.CommentRepository
import com.vmyan.myantrip.utils.Resource
import kotlinx.coroutines.Dispatchers

class CommentViewModel(private val commentRepository: CommentRepository) : ViewModel() {
    fun uploadComment(postId : String,userId : String, description : String,postComment : Int) = liveData(Dispatchers.IO){
        emit(Resource.Loading())
        try {
            val comments = commentRepository.uploadComment(postId,userId,description,postComment)
            emit(comments)
        }catch (e : Exception){
            emit(Resource.Failure(e.message.toString()))
        }
    }

    fun getComment(postId: String) = liveData(Dispatchers.IO) {
        emit(Resource.Loading())
        try {
            val comments = commentRepository.getComment(postId)
            emit(comments)
        }catch (e : Exception){
            emit(Resource.Failure(e.message.toString()))
        }
    }
}