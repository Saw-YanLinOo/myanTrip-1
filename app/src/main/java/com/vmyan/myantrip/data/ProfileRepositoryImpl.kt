package com.vmyan.myantrip.data

import android.net.Uri
import android.util.Log
import androidx.core.net.toUri
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.GeoPoint
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.SetOptions
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.vmyan.myantrip.model.GetPost
import com.vmyan.myantrip.model.Place
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
            val like = document.getLong("like")
            val unlike = document.getLong("unlike")
            val share = document.getLong("share")
            val imglist = document.get("Photo") as ArrayList<String>
            val time = document.getTimestamp("time")
            val comments = document.getLong("comment")
            val place_id = document.getString("place_id")
            val type = document.getLong("type")

            val post = Posts(id,user_id!!,place_id,description!!,imglist!!,like!!,unlike!!,share!!,time!!,comments,type!!.toInt())

            val userResultList = FirebaseFirestore.getInstance()
                .collection("User")
                .document(user_id)
                .get()
                .await()
            val username = userResultList.getString("username")
            val profilephoto = userResultList.getString("profilephoto")
            val backgroundphoto = userResultList.getString("backgroudphoto")
            val followers = userResultList.getLong("followers")
            val followings = userResultList.getLong("followings")
            val user = User(user_id,"","",username!!,profilephoto!!,backgroundphoto,followers,followings)

            var place : Place? = null
            if (place_id!!.trim() != ""){
                val placeResultList = FirebaseFirestore.getInstance()
                    .collection("PlaceList")
                    .document("${place_id.trim()}")
                    .get()
                    .await()
                var placename = placeResultList.getString("name")
                var category = placeResultList.getString("category")
                var placeImgList = placeResultList.get("gallery") as ArrayList<String>

                place = Place("","","",category!!,"","","",placeImgList,"","",
                    GeoPoint(0.0,0.0),"",placename!!,"0.0".toFloat(),ArrayList(),"")

            }

            Log.e("User ==>","${userResultList.id} => ${userResultList.data}")
            Log.e("Post List ==>", "${document.id} => ${document.data}")

            postList.add(GetPost(user,post,place))
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