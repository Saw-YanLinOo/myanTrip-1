package com.vmyan.myantrip.data

import android.content.ContentValues.TAG
import android.os.Environment
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.vmyan.myantrip.model.Word
import com.vmyan.myantrip.model.WordCategory
import com.vmyan.myantrip.utils.Resource
import kotlinx.coroutines.tasks.await
import java.io.*


class CommunicationRepositoryImpl : CommunicationRepository {
    override suspend fun getCategory(): Resource<MutableList<WordCategory>> {
        var categoryList = mutableListOf<WordCategory>()
        var result = FirebaseFirestore.getInstance()
            .collection("CommunicationCategory")
            .get()
            .await()
        for (document in result){
            var id = document.id
            var category = document.getString("title")
            categoryList.add(WordCategory(id, category))
        }
        return Resource.Success(categoryList)
    }

    override suspend fun getWordList(): Resource<MutableList<Word>> {
        var wordList = mutableListOf<Word>()
        var result = FirebaseFirestore.getInstance()
            .collection("Communication")
            .get()
            .await()
        for (document in result){
            Log.d("wordList ", "${document.id} ==> ${document.data}")
            var myn = document.getString("myn")
            var eng = document.getString("eng")
            var mynfile = document.getString("myn_file")
            var engfile = document.getString("eng_file")

            wordList.add(Word("", eng, engfile, "", myn, mynfile))
        }

        return Resource.Success(wordList)
    }

    override suspend fun getWordListFromD(): Resource<MutableList<Word>> {
        val menu: MutableList<Word> = mutableListOf()

        var firebaseData = FirebaseDatabase.getInstance().reference
            .child("2").child("data")


        val menuListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (ds in dataSnapshot.children) {
                    val category = ds.child("category").getValue(String::class.java)!!
                    val myn = ds.child("myn").getValue(String::class.java)
                    val eng = ds.child("eng").getValue(String::class.java)
                    val myn_file = ds.child("myn_file").getValue(String::class.java)
                    val eng_file = ds.child("eng_file").getValue(String::class.java)
                    Log.d(TAG, "$myn / $eng")
                    var word = Word("", eng, eng_file, "", myn, myn_file)
                    println("WordList ===> ${menu}")

                    val hashMap : HashMap<String, Any> = HashMap()


                    if (category.trim() == "greetings" ){
                        hashMap["category_id"] = "5O7Q7XbqgYei4c1xenaM"
                    }
                    if (category.trim() == "feeling expression"){
                        hashMap["category_id"] = "ci8m8Bxjc068eDL9Jazc"
                    }
                    if (category.trim() == "emergency" ){
                        hashMap["category_id"] = "zplRlTl7dZ8mucNFBrRc"
                    }
                    if (category.trim() == "restaurant"){
                        hashMap["category_id"] = "E60mQ4BO79TsRNy6cu02"
                    }
                    if (category.trim() == "places_directory" ){
                        hashMap["category_id"] = "G9NDU8VwqSitMCK9EE4a"
                    }
                    hashMap["myn"] = word.myn.toString()
                    hashMap["eng"] = word.eng.toString()
                    hashMap["myn_file"] = word.myn_file.toString()
                    hashMap["eng_file"] = word.eng_file.toString()


                    val result = FirebaseFirestore.getInstance()
                        .collection("Communication")
                        .add(hashMap)

                    println("set ==> ${result}")
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                println("loadPost:onCancelled ${databaseError.toException()}")
            }
        }

        firebaseData.addListenerForSingleValueEvent(menuListener)

        return Resource.Success(menu)
    }

    override suspend fun getWordByCategory(categoryId: String): Resource<MutableList<Word>> {
        var wordList = mutableListOf<Word>()
        var result = FirebaseFirestore.getInstance()
            .collection("Communication")
            .whereEqualTo("category_id", categoryId)
            .get()
            .await()
        for (document in result){
            Log.d("wordList ", "${document.id} ==> ${document.data}")
            var myn = document.getString("myn")
            var eng = document.getString("eng")
            var mynfile = document.getString("myn_file")
            var engfile = document.getString("eng_file")

            wordList.add(Word("", eng, engfile, "", myn, mynfile))
        }

        return Resource.Success(wordList)
    }
}