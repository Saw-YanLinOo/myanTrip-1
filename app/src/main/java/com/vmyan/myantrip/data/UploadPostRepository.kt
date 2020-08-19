package com.vmyan.myantrip.data

import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.DocumentReference
import com.vmyan.myantrip.model.Posts
import com.vmyan.myantrip.utils.Resource

interface UploadPostRepository {
    suspend fun setPost(posts: Posts) : Resource<MutableLiveData<DocumentReference>>
    suspend fun uploadPhotos(data : ArrayList<String>) : Resource<MutableLiveData<ArrayList<String>>>
}