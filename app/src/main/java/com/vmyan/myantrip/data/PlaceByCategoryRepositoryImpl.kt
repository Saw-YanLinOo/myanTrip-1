package com.vmyan.myantrip.data

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.vmyan.myantrip.model.Place
import com.vmyan.myantrip.model.PlaceByCategory
import com.vmyan.myantrip.model.Review
import com.vmyan.myantrip.model.SubPlaceCategory
import com.vmyan.myantrip.utils.Resource
import kotlinx.coroutines.tasks.await

class PlaceByCategoryRepositoryImpl : PlaceByCategoryRepository {
    override suspend fun getPlaceByCategory(cat_id: String): Resource<MutableList<PlaceByCategory>> {
        val placeList = mutableListOf<PlaceByCategory>()
        val resultList = FirebaseFirestore.getInstance()
            .collection("/PlaceList/"+cat_id+"/SubCategory")
            .get()
            .await()

        for (data in resultList) {
            val id = data.id
            val name = data.getString("name")
            val pList = mutableListOf<Place>()
            val rList = FirebaseFirestore.getInstance()
                .collection("/PlaceList/"+cat_id+"/SubCategory/"+id+"/Place")
                .orderBy("ratingValue", Query.Direction.DESCENDING)
                .get()
                .await()
            for (document in rList) {
                val pid = document.id
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
                val pname = document.getString("name")
                val ratingValue = document.getDouble("ratingValue")?.toFloat()
                val sliderImg = document.get("sliderImg") as ArrayList<String>
                val state = document.getString("state")


                pList.add(Place(pid, address!!, buildDate!!, category!!, city!!, country!!, founder!!, gallery, history!!, info!!, latlng!!, mainImg!!, pname!!, ratingValue!!, sliderImg, state!!))
            }

            placeList.add(PlaceByCategory(SubPlaceCategory(id,name!!), pList))
        }

        return Resource.Success(placeList)
    }

}