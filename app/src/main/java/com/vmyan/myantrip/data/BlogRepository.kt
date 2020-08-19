package com.vmyan.myantrip.data


import com.vmyan.myantrip.model.Comments
import com.vmyan.myantrip.model.GetPost
import com.vmyan.myantrip.utils.Resource

interface BlogRepository {
    suspend fun getAllPost() : Resource<MutableList<GetPost>>
    suspend fun getComment(postid : String) : Resource<MutableList<Comments>>
}