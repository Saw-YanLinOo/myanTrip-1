package com.vmyan.myantrip.data

import com.google.firebase.firestore.FirebaseFirestore
import com.vmyan.myantrip.model.Place
import com.vmyan.myantrip.model.PlaceCategory
import com.vmyan.myantrip.model.SubPlaceCategory
import com.vmyan.myantrip.utils.Resource
import kotlinx.coroutines.tasks.await

class HomeRepositoryImpl : HomeRepository {
    override suspend fun getPlaceCategory(): Resource<MutableList<PlaceCategory>> {
        val catList = mutableListOf<PlaceCategory>()
        val resultList = FirebaseFirestore.getInstance()
            .collection("PlaceList")
            .get()
            .await()

        for (document in resultList){
            val cat_id = document.id
            val cat_name = document.getString("cat_name")
            val cat_img_url = document.getString("cat_img_url")
            catList.add(PlaceCategory(cat_id,cat_name!!, cat_img_url!!))
        }

        return Resource.Success(catList)
    }

    override suspend fun getSubPlaceCategory(): Resource<MutableList<SubPlaceCategory>> {
        val subCatList = mutableListOf<SubPlaceCategory>()
        val resultList = FirebaseFirestore.getInstance()
            .collection("SubCategory")
            .get()
            .await()

        for (document in resultList) {
            val id = document.id
            val name = document.getString("name")
            subCatList.add(SubPlaceCategory(id,name!!))
        }

        return Resource.Success(subCatList)
    }

    override suspend fun getPlaceBySubCategory(sub_cat_id: String): Resource<MutableList<Place>> {
        val placeList = mutableListOf<Place>()

        val catList = FirebaseFirestore.getInstance()
            .collection("PlaceList")
            .get()
            .await()

        for (data in catList){
            val resultList = FirebaseFirestore.getInstance()
                .collection("/PlaceList/"+data.id+"/SubCategory/"+sub_cat_id+"/Place")
                .whereGreaterThanOrEqualTo("ratingValue",4)
//                .orderBy("ratingValue",Query.Direction.DESCENDING)
                .limit(5)
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

        return Resource.Success(placeList)
    }

}