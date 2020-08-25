package com.vmyan.myantrip.data

import androidx.lifecycle.MutableLiveData
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import com.vmyan.myantrip.model.Comments
import com.vmyan.myantrip.model.User
import com.vmyan.myantrip.utils.Resource
import kotlinx.coroutines.tasks.await

class CommentRepositoryImpl : CommentRepository {
    override suspend fun getComment(postId : String): Resource<MutableList<Comments>> {
        val commentList = mutableListOf<Comments>()

        val commentResultList = FirebaseFirestore.getInstance()
            .collection("Post")
            .document(postId)
            .collection("Comment")
            .get()
            .await()

        for (document in commentResultList){
            val userId = document.getString("user_id")
            val description = document.getString("description")
            val time = document.getTimestamp("time")
            val userResultList = FirebaseFirestore.getInstance()
                .collection("User")
                .document(userId!!)
                .get()
                .await()
            val username = userResultList.getString("username")
            val profilephoto = userResultList.getString("profilephoto")

            val user = User(userId,"","",username!!,profilephoto!!,"",0,0)
            commentList.add(Comments(user,description,time))
        }
        return Resource.Success(commentList)
    }

    override suspend fun uploadComment(postId: String, userId : String, description : String,postComments: Int): Resource<MutableList<Comments>> {

        val comments = hashMapOf(
            "description" to description,
            "user_id" to userId,
            "time" to Timestamp.now(),
        )
        val commentResultList = FirebaseFirestore.getInstance()
            .collection("Post")
            .document(postId)
            .collection("Comment")
            .document()
            .set(comments)
            .await()

        val updateComment = FirebaseFirestore.getInstance()
            .collection("Post")
            .document(postId)
            .update("comment",postComments + 1)

        var result = getComment(postId)

        return result
    }
}