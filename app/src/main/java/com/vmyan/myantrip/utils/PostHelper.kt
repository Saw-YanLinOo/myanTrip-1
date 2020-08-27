package com.vmyan.myantrip.utils

import android.text.BoringLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import java.util.*

class PostHelper {
    private val firebaseFirestore = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()

    fun updateLike(postId : String){
        println("Post id ===> ${postId}")
        //chech current user like
        firebaseFirestore.collection("Post/$postId/Like").document(auth.currentUser!!.uid).get().addOnCompleteListener {
            if (!it.getResult()!!.exists()){
                var likeMap : HashMap<String, Any> = HashMap()
                likeMap.put("time",FieldValue.serverTimestamp())
                firebaseFirestore.collection("Post/$postId/Like").document(auth.currentUser!!.uid).set(likeMap)
            }else{
                firebaseFirestore.collection("Post/$postId/Like").document(auth.currentUser!!.uid).delete()
            }
        }
    }

    fun updateUnLike(postId : String){
        println("Post id ===> ${postId}")
        firebaseFirestore.collection("Post/$postId/UnLike").document(auth.currentUser!!.uid).get().addOnCompleteListener {
            if (!it.getResult()!!.exists()){
                var likeMap : HashMap<String, Any> = HashMap()
                likeMap.put("time",FieldValue.serverTimestamp())
                firebaseFirestore.collection("Post/$postId/UnLike").document(auth.currentUser!!.uid).set(likeMap)
            }else{
                firebaseFirestore.collection("Post/$postId/UnLike").document(auth.currentUser!!.uid).delete()
            }
        }
    }
}