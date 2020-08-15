package com.vmyan.myantrip.data

import com.vmyan.myantrip.model.Posts
import com.vmyan.myantrip.utils.Resource

interface BlogRepository {
    suspend fun getPost() : Resource<MutableList<Posts>>
}