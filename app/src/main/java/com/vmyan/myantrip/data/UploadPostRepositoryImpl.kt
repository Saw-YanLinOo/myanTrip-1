package com.vmyan.myantrip.data

import android.R.attr.data
import android.R.attr.targetActivity
import android.content.Intent
import android.content.Intent.FLAG_GRANT_READ_URI_PERMISSION
import android.content.Intent.FLAG_GRANT_WRITE_URI_PERMISSION
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
import java.io.File
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap


class UploadPostRepositoryImpl : UploadPostRepository {
    val auth: FirebaseAuth = FirebaseAuth.getInstance()
    override suspend fun setPost(posts: Posts): Resource<MutableLiveData<DocumentReference>> {

        val hashMap : HashMap<String, Any> = HashMap()

        hashMap["Photo"] = posts.image_url
        hashMap["description"] = posts.description as String
        hashMap["user_id"] = auth.currentUser!!.uid
        hashMap["like"] = posts.like as Long
        hashMap["unlike"] = posts.unlike as Long
        hashMap["share"] = posts.share as Long
        hashMap["time"] = Timestamp.now()
        hashMap["comment"] = posts.comments as Long
        hashMap["place_id"] = posts.place_id as String
        hashMap["type"] = posts.type as String

        println("Upload Post===> $hashMap")
        val result = FirebaseFirestore.getInstance()
            .collection("Post")
            .add(hashMap).await()

        return Resource.Success(MutableLiveData(result))
    }

    override suspend fun uploadPhotos(photosUri: ArrayList<String>): Resource<MutableLiveData<ArrayList<String>>> {
        val storageRef = FirebaseStorage.getInstance().getReference().child("Post")
        val photosUrls = ArrayList<String>()
        println("Photo List ==> $photosUri")

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


        try {
            val file = File(photoFile)
            println("file Url ==> {${photoFile.toUri()}")
            println("file  ===> $file")
            storageRef.child(fileName)
                .putFile(fileUri)
                .await()
                .storage
                .downloadUrl
                .await()
        }catch (e: Exception){
            println("${e.printStackTrace()}")
        }

        return storageRef.child(fileName)
            .putFile(fileUri)
            .await()
            .storage
            .downloadUrl
            .await()
    }
}