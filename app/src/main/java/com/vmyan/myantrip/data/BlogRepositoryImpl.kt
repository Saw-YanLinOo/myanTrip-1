package com.vmyan.myantrip.data

import android.content.ContentValues
import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.vmyan.myantrip.model.Posts
import com.vmyan.myantrip.utils.Resource
import kotlinx.coroutines.tasks.await

class BlogRepositoryImpl:BlogRepository {
    override suspend fun getPost(): Resource<MutableList<Posts>> {
        val postList = mutableListOf<Posts>()
        val imagList= mutableListOf<String>()
        val resultList = FirebaseFirestore.getInstance()
            .collection("Post")
            .get()
            .await()

        for (document in resultList){
            Log.e(ContentValues.TAG, "${document.id} => ${document.data}")
            val id = document.id
            val user_id = document.getString("user_id")
            val description = document.getString("description")
            val like = document.getString("like")
            val share = document.getString("share")

            var image = FirebaseFirestore.getInstance()
                .collection("Post")
                .document(document.id)
                .collection("Photo")
                .get()
                .await()
            for (result in image){
                val url = result.getString("url")
                imagList.add(url!!)
            }
            postList.add(Posts(id,user_id!!,description!!,imagList!!,like!!,share!!))
        }

        return Resource.Success(postList)
    }
}