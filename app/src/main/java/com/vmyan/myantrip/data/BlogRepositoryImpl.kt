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
import com.google.firebase.firestore.Query
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.vmyan.myantrip.model.*
import com.vmyan.myantrip.utils.Resource
import kotlinx.coroutines.*
import kotlinx.coroutines.tasks.await
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class BlogRepositoryImpl:BlogRepository {

    override suspend fun getAllPost(): Resource<MutableList<GetPost>> {
        val postList = mutableListOf<GetPost>()

        val postResultList = FirebaseFirestore.getInstance()
            .collection("Post")
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
            val post = Posts(id,user_id!!,description!!, imglist,like!!,share!!,time!!)

            val userResultList = FirebaseFirestore.getInstance()
                .collection("User")
                .document(user_id)
                .get()
                .await()
            val username = userResultList.getString("username")
            val profilephoto = userResultList.getString("profilephoto")
            val user = User(user_id,"","",username!!,profilephoto!!,"","0","0")

            postList.add(GetPost(user,post))
        }


        return Resource.Success(postList)
    }

    override suspend fun getComment(postid : String): Resource<MutableList<Comments>> {
        val commentList = mutableListOf<Comments>()

        val commentResultList = FirebaseFirestore.getInstance()
            .collection("Post")
            .document(postid)
            .collection("Comment")
            .get()
            .await()

        for (document in commentResultList){
            val userid = document.getString("userid")
            val description = document.getString("description")

            val userResultList = FirebaseFirestore.getInstance()
                .collection("User")
                .document(userid!!)
                .get()
                .await()
            val username = userResultList.getString("username")
            val profilephoto = userResultList.getString("profilephoto")

            commentList.add(Comments(username!!,profilephoto!!,description!!))
        }
            return Resource.Success(commentList)
    }

}