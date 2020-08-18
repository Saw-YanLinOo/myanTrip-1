package com.vmyan.myantrip.data

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.vmyan.myantrip.model.Place
import com.vmyan.myantrip.utils.Resource
import kotlinx.coroutines.tasks.await

class SearchPlaceRepositoryImpl : SearchPlaceRepository {
    override suspend fun getPlaceBySearch(): Resource<MutableList<Place>> {
        val placeList = mutableListOf<Place>()

        val catList = FirebaseFirestore.getInstance()
            .collection("/PlaceList")
            .get()
            .await()

        for (cat in catList){
            val cid = cat.id
            val subList = FirebaseFirestore.getInstance()
                .collection("/PlaceList/$cid/SubCategory")
                .get()
                .await()
            for (sub in subList){
                val sid = sub.id
                val resultList = FirebaseFirestore.getInstance()
                    .collection("/PlaceList/$cid/SubCategory/$sid/Place")
                    .orderBy("name",Query.Direction.ASCENDING)
                    .get()
                    .await()

                for (document in resultList){
                    val id = document.id
                    val address = document.getString("address")
                    val buildDate = document.getString("buildDate")
                    val category = document.getString("category")
                    val city = document.getString("city")
                    val country = document.getString("country")
                    val founder = document.getString("founder")
                    val gallery = document.get("gallery") as ArrayList<String>
                    val history = document.getString("history")
                    val info = document.getString("info")
                    val latlng = document.getGeoPoint("latlng")
                    val mainImg = document.getString("mainImg")
                    val name = document.getString("name")
                    val ratingValue = document.getDouble("ratingValue")?.toFloat()
                    val sliderImg = document.get("sliderImg") as ArrayList<String>
                    val state = document.getString("state")



                    placeList.add(
                        Place(id, address!!, buildDate!!, category!!, city!!, country!!, founder!!, gallery, history!!, info!!, latlng!!, mainImg!!, name!!, ratingValue!!, sliderImg, state!!)
                    )

                }
            }
        }
        return Resource.Success(placeList)
    }

}