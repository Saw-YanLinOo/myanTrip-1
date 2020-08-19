package com.vmyan.myantrip.data

import android.net.Uri
import android.util.Log
import androidx.core.net.toUri
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.SetOptions
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.vmyan.myantrip.model.GetPost
import com.vmyan.myantrip.model.Posts
import com.vmyan.myantrip.model.User
import com.vmyan.myantrip.utils.Resource
import kotlinx.coroutines.*
import kotlinx.coroutines.tasks.await
import java.util.*
import kotlin.collections.ArrayList

class ProfileRepositoryImpl : ProfileRepository {
    private val auth = FirebaseAuth.getInstance()
    override suspend fun getProfile(user_id: String): Resource<MutableList<GetPost>> {
        val postList = mutableListOf<GetPost>()

        val postResultList = FirebaseFirestore.getInstance()
            .collection("Post")
            .whereEqualTo("user_id",user_id)
            .get()
            .await()

        for (document in postResultList){

            val id = document.id
            val user_id = document.getString("user_id")
            val description = document.getString("description")
            val like = document.getString("like")
            val share = document.getString("share")
            val imglist = document.get("Photo") as ArrayList<String>
            val time = document.getTimestamp("time")
            val post = Posts(id,user_id!!,description!!,imglist!!,like!!,share!!,time!!)

            val userResultList = FirebaseFirestore.getInstance()
                .collection("User")
                .document(user_id)
                .get()
                .await()
            val username = userResultList.getString("username")
            val profilephoto = userResultList.getString("profilephoto")
            //val backgroundphoto = userResultList.getString("backgroundphoto")
            //val followers = userResultList.getString("followers")
            //val followings = userResultList.getString("followings")

            val user = User(user_id,"","",username!!,profilephoto!!,"","","")

            Log.e("User ==>","${userResultList.id} => ${userResultList.data}")
            Log.e("Post List ==>", "${document.id} => ${document.data}")

            postList.add(GetPost(user,post))
        }

        return Resource.Success(postList)
    }

    override suspend fun updateProfilePhoto(uri: String): Resource<MutableLiveData<String>> {
        val storageRef = FirebaseStorage.getInstance().getReference().child("UserPhoto")
        val uploadedPhotosUriLink = withContext(CoroutineScope(Dispatchers.IO).coroutineContext) {
                async(Dispatchers.IO) {
                    uploadPhoto(storageRef, uri)
                }
        }.await()
        val data = hashMapOf("profilephoto" to uploadedPhotosUriLink.toString())
        val result = FirebaseFirestore.getInstance()
            .collection("User")
            .document(auth.currentUser!!.uid)
            .set(data, SetOptions.merge()).await()
        println("Updated profile ==> ${result}")

        return Resource.Success(MutableLiveData(uploadedPhotosUriLink.toString()))
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