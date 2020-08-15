package com.vmyan.myantrip.data

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.vmyan.myantrip.model.User
import com.vmyan.myantrip.model.UserAccountSetting
import com.vmyan.myantrip.utils.Resource
import kotlinx.coroutines.tasks.await

class LoginRepositoryImpl:LoginRepository {
    val auth: FirebaseAuth = FirebaseAuth.getInstance()
    val mFirebaseFirestore: FirebaseFirestore = FirebaseFirestore.getInstance()

    override suspend fun getCurrentUser(): Resource<MutableLiveData<User>> {

        val muser = FirebaseFirestore.getInstance()
            .collection("User")
            .document(auth.currentUser!!.uid)
            .get()
            .await()
        var mUser = muser.data
        var user = User(auth.currentUser!!.uid,mUser!!["phone_number"].toString(),mUser!!["email"].toString(),mUser!!["username"].toString(),mUser!!["profilephoto"].toString())
        return Resource.Success(MutableLiveData(user))
    }
    override suspend fun signIn(email: String, password: String): Resource<MutableLiveData<AuthResult>> {
        return Resource.Success(MutableLiveData(auth.signInWithEmailAndPassword(email, password).await()))
    }

    override suspend fun signUp(email: String, password: String): Resource<MutableLiveData<AuthResult>> {
        return Resource.Success(MutableLiveData(auth.createUserWithEmailAndPassword(email,password).await()))
    }

    override suspend fun addNewUser(user: User) {
        val mUser = User(auth.currentUser!!.uid, user.phone_number, user.email, user.username,user.profilephoto)
        mFirebaseFirestore.collection("User").document(auth.currentUser!!.uid).set(mUser)
    }

    override suspend fun addUserAccountSetting(userAccountSetting: UserAccountSetting) {
        val mUserAccountSetting =
            UserAccountSetting(userAccountSetting.description,
                userAccountSetting.followers,
                userAccountSetting.followings,
                userAccountSetting.posts,
                userAccountSetting.profilephoto,
                userAccountSetting.username,
                userAccountSetting.website)
        var r= mFirebaseFirestore.collection("UserAccountSetting").document(auth.currentUser!!.uid).set(mUserAccountSetting)
    }


}