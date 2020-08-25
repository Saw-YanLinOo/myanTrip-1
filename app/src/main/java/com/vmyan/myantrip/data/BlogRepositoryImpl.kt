package com.vmyan.myantrip.data

import android.content.ContentValues
import android.net.Uri
import android.util.Log
import androidx.core.net.toUri
import androidx.lifecycle.MutableLiveData
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.GeoPoint
import com.google.firebase.firestore.Query
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.vmyan.myantrip.model.*
import com.vmyan.myantrip.module.addPlanModule
import com.vmyan.myantrip.utils.Resource
import kotlinx.coroutines.*
import kotlinx.coroutines.tasks.await
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class BlogRepositoryImpl:BlogRepository {
    override suspend fun getAllUser(): Resource<MutableList<User>> {
        val userList = mutableListOf<User>()

        val userResultList = FirebaseFirestore.getInstance()
            .collection("User")
            .get()
            .await()

        for (document in userResultList){
            var id = document.id
            var username = document.getString("username")
            var profilephoto = document.getString("profilephoto")

            userList.add(User(id,"","",username!!,profilephoto!!,"",0,0))
        }

        return Resource.Success(userList)
    }

    override suspend fun getAllPost(): Resource<MutableList<GetPost>> {
        val postList = mutableListOf<GetPost>()
        postList.clear()
        val postResultList = FirebaseFirestore.getInstance()
            .collection("Post")
            .orderBy("time",Query.Direction.DESCENDING)
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
            val type = document.getString("type")

            var post = Posts(id,user_id!!,"",description!!,imglist!!,like!!,unlike!!,share!!,time!!,comments,"")
            Log.e("Post List ==>", "${document.id} => ${post}")

            val userResultList = FirebaseFirestore.getInstance()
                .collection("User")
                .document(user_id)
                .get()
                .await()
            val username = userResultList.getString("username")
            val profilephoto = userResultList.getString("profilephoto")
            var user = User(user_id,"","",username!!,profilephoto!!,"",0,0)
            Log.e("User ==>","${userResultList.id} => ${user}")

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

            postList.add(GetPost(user,post,place))
            Log.e("Get Post ==>"," => ${postList}")
        }

        return Resource.Success(postList)
    }

}