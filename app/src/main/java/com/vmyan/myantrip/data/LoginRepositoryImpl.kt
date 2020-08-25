package com.vmyan.myantrip.data

import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.vmyan.myantrip.model.User
import com.vmyan.myantrip.utils.Resource
import kotlinx.coroutines.tasks.await

class LoginRepositoryImpl:LoginRepository {
    val auth: FirebaseAuth = FirebaseAuth.getInstance()

    override suspend fun getCurrentUser(id:String): Resource<MutableLiveData<User>> {

        val muser = FirebaseFirestore.getInstance()
            .collection("User")
            .document(id)
            .get()
            .await()
        var mUser = muser.data
        var user = User(auth.currentUser!!.uid,mUser!!["phone_number"].toString(),mUser!!["email"].toString(),mUser!!["username"].toString(),mUser!!["profilephoto"].toString(),mUser!!["backgroundprofile"].toString())
        return Resource.Success(MutableLiveData(user))
    }
    override suspend fun signIn(email: String, password: String): Resource<MutableLiveData<AuthResult>> {
        return Resource.Success(MutableLiveData(auth.signInWithEmailAndPassword(email, password).await()))
    }

}