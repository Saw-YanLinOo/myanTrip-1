package com.vmyan.myantrip.data

import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.vmyan.myantrip.model.User
import com.vmyan.myantrip.utils.Resource
import kotlinx.coroutines.tasks.await

class RegisterRepositoryImpl : RegisterRepository {
    val auth: FirebaseAuth = FirebaseAuth.getInstance()
    val mFirebaseFirestore: FirebaseFirestore = FirebaseFirestore.getInstance()

    override suspend fun getCurrentUser(id:String): Resource<MutableLiveData<User>> {

        val muser = FirebaseFirestore.getInstance()
            .collection("User")
            .document(id)
            .get()
            .await()
        var mUser = muser.data
        var user = User(auth.currentUser!!.uid,mUser!!["phone_number"].toString(),mUser!!["email"].toString(),mUser!!["username"].toString(),mUser!!["profilephoto"].toString(),mUser["backgroundprofile"].toString(),mUser!!["followers"].toString(),mUser!!["followings"].toString())
        return Resource.Success(MutableLiveData(user))
    }

    override suspend fun signUp(email: String, password: String): Resource<MutableLiveData<AuthResult>> {
        return Resource.Success(MutableLiveData(auth.createUserWithEmailAndPassword(email,password).await()))
    }

    override suspend fun addNewUser(user: User) {
        val mUser = User(auth.currentUser!!.uid, user.phone_number, user.email, user.username,user.profilephoto,user.backgroudphoto,user.followers,user.followings)
        mFirebaseFirestore.collection("User").document(auth.currentUser!!.uid).set(mUser)
    }
}