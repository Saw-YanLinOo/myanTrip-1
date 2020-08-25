package com.vmyan.myantrip.data

import com.google.firebase.firestore.FieldPath
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.vmyan.myantrip.model.User
import com.vmyan.myantrip.utils.Resource
import kotlinx.coroutines.tasks.await

class TripPlanUserRepositoryImpl : TripPlanUserRepository{
    override suspend fun getTeammates(tripId: String): Resource<MutableList<User>> {

        val userList = mutableListOf<User>()

        val result = FirebaseFirestore.getInstance()
            .collection("TripList")
            .document(tripId)
            .get()
            .await()

        val users = result.get("userId") as ArrayList<String>

        for (data in users){
            val uResult = FirebaseFirestore.getInstance()
                .collection("User")
                .whereEqualTo(FieldPath.documentId(),data)
                .get()
                .await()

            for (user in uResult){
                val id = user.id
                val username = user.getString("username")
                val backgroundphoto = user.getString("backgroudphoto")
                val email = user.getString("email")
                val followers = user.getLong("followers")
                val followings = user.getLong("followings")
                val phonenumbar = user.getString("phone_number")
                val userImg = user.getString("profilephoto")

                userList.add(User(id,phonenumbar!!,email!!,username!!,userImg!!,backgroundphoto!!,followers!!,followings!!))
            }
        }

        return Resource.Success(userList)
    }

    override suspend fun addTeammate(tripId: String, userEmail: String): Resource<String> {
        val user = FirebaseFirestore.getInstance()
            .collection("User")
            .whereEqualTo("email", userEmail)
            .get().await()

        val trip = FirebaseFirestore.getInstance()
            .collection("TripList")
            .document(tripId)
            .get().await()

        val uList = trip.get("userId") as ArrayList<String>

        for (data in user){
            for (u in uList){
                if (u != data.id){
                    FirebaseFirestore.getInstance()
                        .collection("TripList")
                        .document(tripId)
                        .update("userId",FieldValue.arrayUnion(data.id))
                }
            }
        }

        return if (user.isEmpty){
            Resource.Failure("email not exists")
        }else{
            Resource.Success("success")
        }

    }

}