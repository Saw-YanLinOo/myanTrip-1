package com.vmyan.myantrip.data


import com.vmyan.myantrip.model.Comments
import com.vmyan.myantrip.model.GetPost
import com.vmyan.myantrip.model.User
import com.vmyan.myantrip.utils.Resource

interface BlogRepository {
    suspend fun getAllUser() : Resource<MutableList<User>>
    suspend fun getAllPost() : Resource<MutableList<GetPost>>
}