package com.vmyan.myantrip.data

import androidx.lifecycle.MutableLiveData
import com.vmyan.myantrip.model.Comments
import com.vmyan.myantrip.utils.Resource

interface CommentRepository {
    suspend fun getComment(postId : String) : Resource<MutableList<Comments>>
    suspend fun uploadComment(postId : String, userId : String, description : String,postComments : Int) : Resource<MutableList<Comments>>
}