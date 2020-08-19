package com.vmyan.myantrip.data

import android.net.Uri
import androidx.core.net.toUri
import androidx.lifecycle.MutableLiveData
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.vmyan.myantrip.model.Posts
import com.vmyan.myantrip.utils.Resource
import kotlinx.coroutines.*
import kotlinx.coroutines.tasks.await
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class UploadPostRepositoryImpl : UploadPostRepository {
    val auth: FirebaseAuth = FirebaseAuth.getInstance()
    override suspend fun setPost(posts: Posts): Resource<MutableLiveData<DocumentReference>> {

        val hashMap : HashMap<String,Any> = HashMap()

        hashMap["Photo"] = posts.image_url
        hashMap["description"] = posts.description
        hashMap["user_id"] = auth.currentUser!!.uid
        hashMap["like"] = posts.like
        hashMap["share"] = posts.share
        hashMap["time"] = Timestamp.now()

        val result = FirebaseFirestore.getInstance()
            .collection("Post")
            .add(hashMap).await()

        return Resource.Success(MutableLiveData(result))
    }

    override suspend fun uploadPhotos(photosUri: ArrayList<String>): Resource<MutableLiveData<ArrayList<String>>> {
        val storageRef = FirebaseStorage.getInstance().getReference().child("Post")
        val photosUrls = ArrayList<String>()
        val uploadedPhotosUriLink = withContext(CoroutineScope(Dispatchers.IO).coroutineContext) {
            (photosUri.indices).map { index ->
                async(Dispatchers.IO) {
                    uploadPhoto(storageRef, photosUri[index])
                }
            }
        }.awaitAll()

        uploadedPhotosUriLink.forEach { photoUriLink -> photosUrls.add(photoUriLink.toString()) }
        return Resource.Success(MutableLiveData(photosUrls))
    }


    private suspend fun uploadPhoto(storageRef: StorageReference, photoFile: String): Uri {
        val fileName = UUID.randomUUID().toString()
        val fileUri = photoFile.toUri()

        return storageRef.child(fileName)
            .putFile(fileUri)
            .await()
            .storage
            .downloadUrl
            .await()
    }
}