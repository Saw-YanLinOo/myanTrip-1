package com.vmyan.myantrip.data

import com.google.firebase.firestore.FieldPath
import com.google.firebase.firestore.FirebaseFirestore
import com.vmyan.myantrip.model.Place
import com.vmyan.myantrip.utils.Resource
import kotlinx.coroutines.tasks.await

class PlaceDetailsRepositoryImpl : PlaceDetailsRepository {
    override suspend fun getPlaceDetails(place_id: String): Resource<MutableList<Place>> {
        val place = mutableListOf<Place>()

        val catList = FirebaseFirestore.getInstance()
            .collection("PlaceList")
            .get()
            .await()

        for (cat in catList){
            val subList = FirebaseFirestore.getInstance()
                .collection("/PlaceList/"+cat.id+"/SubCategory")
                .get()
                .await()
            for (sub in subList){
                val resultList = FirebaseFirestore.getInstance()
                    .collection("/PlaceList/"+cat.id+"/SubCategory/"+sub.id+"/Place")
                    .whereEqualTo(FieldPath.documentId(), place_id)
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

                    place.add(
                        Place(
                            id,
                            address!!,
                            buildDate!!,
                            category!!,
                            city!!,
                            country!!,
                            founder!!,
                            gallery,
                            history!!,
                            info!!,
                            latlng!!,
                            mainImg!!,
                            name!!,
                            ratingValue!!,
                            sliderImg,
                            state!!
                        )
                    )
                }
            }

        }
        return Resource.Success(place)
    }

}