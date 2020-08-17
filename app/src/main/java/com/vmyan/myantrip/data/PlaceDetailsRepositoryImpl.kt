package com.vmyan.myantrip.data

import com.google.firebase.Timestamp
import com.google.firebase.firestore.FieldPath
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.vmyan.myantrip.model.Place
import com.vmyan.myantrip.model.PlaceDetails
import com.vmyan.myantrip.model.Review
import com.vmyan.myantrip.utils.Resource
import kotlinx.coroutines.tasks.await

class PlaceDetailsRepositoryImpl : PlaceDetailsRepository {
    override suspend fun getPlaceDetails(place_id: String): Resource<MutableList<PlaceDetails>> {
        val placeDetails = mutableListOf<PlaceDetails>()

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

                    val reviewList = mutableListOf<Review>()
                    val reviewResult = FirebaseFirestore.getInstance()
                        .collection("/PlaceList/"+cat.id+"/SubCategory/"+sub.id+"/Place/"+document.id+"/Review")
                        .orderBy("date", Query.Direction.DESCENDING)
                        .get()
                        .await()
                    for (review in reviewResult){
                        val rid = review.id
                        val user_id = review.getString("user_id")
                        val user_name = review.getString("user_name")
                        val user_img = review.getString("user_img")
                        val rating_val = review.getDouble("rating_val")!!.toFloat()
                        val desc = review.getString("desc")
                        val date = review.getTimestamp("date")

                        reviewList.add(Review(rid, user_id!!, user_name!!, user_img!!, rating_val, desc!!, date!!))

                    }

                    placeDetails.add(
                        PlaceDetails(
                            cat.id,sub.id,
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
                        ),
                        reviewList
                    )
                    )

                }
            }

        }


        return Resource.Success(placeDetails)
    }

    override suspend fun getNearByPlace(city: String): Resource<MutableList<Place>> {
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
                    .whereEqualTo("city",city)
//                    .orderBy("ratingValue",Query.Direction.DESCENDING)
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

    override suspend fun addReview(
        cat_name: String,
        subcat_id: String,
        place_id: String,
        userid: String,
        username: String,
        userImg: String,
        rating: Float,
        desc: String,
        date: Timestamp
    ): Resource<String> {
        val data = hashMapOf(
            "date" to date,
            "desc" to desc,
            "rating_val" to rating,
            "user_id" to userid,
            "user_name" to username,
            "user_img" to userImg
        )
        FirebaseFirestore.getInstance()
            .collection("/PlaceList/$cat_name/SubCategory/$subcat_id/Place/$place_id/Review")
            .add(data)
            .await()

        val reviewList = mutableListOf<Review>()
        val reviewResult = FirebaseFirestore.getInstance()
            .collection("/PlaceList/$cat_name/SubCategory/$subcat_id/Place/$place_id/Review")
            .get()
            .await()
        for (review in reviewResult){
            val rid = review.id
            val user_id = review.getString("user_id")
            val user_name = review.getString("user_name")
            val user_img = review.getString("user_img")
            val rating_val = review.getDouble("rating_val")!!.toFloat()
            val desc = review.getString("desc")
            val date = review.getTimestamp("date")

            reviewList.add(Review(rid, user_id!!, user_name!!, user_img!!, rating_val, desc!!, date!!))

        }

        var avg = 0f
        for (r in reviewList){
            avg+= r.rating_val
        }
        var rVal = (avg/reviewList.size)

        FirebaseFirestore.getInstance().collection("/PlaceList/$cat_name/SubCategory/$subcat_id/Place")
            .document(place_id)
            .update(mapOf(
                "ratingValue" to rVal
            ))

        return Resource.Success("Success")
    }

    override suspend fun updateReview(
        cat_name: String,
        subcat_id: String,
        place_id: String,
        review_id: String,
        rating: Float,
        desc: String,
        date: Timestamp
    ): Resource<String> {
        val data = mapOf(
            "date" to date,
            "desc" to desc,
            "rating_val" to rating
        )
        FirebaseFirestore.getInstance()
            .collection("/PlaceList/$cat_name/SubCategory/$subcat_id/Place/$place_id/Review/").document(review_id)
            .update(data)
            .await()

        val reviewList = mutableListOf<Review>()
        val reviewResult = FirebaseFirestore.getInstance()
            .collection("/PlaceList/$cat_name/SubCategory/$subcat_id/Place/$place_id/Review")
            .get()
            .await()
        for (review in reviewResult){
            val rid = review.id
            val user_id = review.getString("user_id")
            val user_name = review.getString("user_name")
            val user_img = review.getString("user_img")
            val rating_val = review.getDouble("rating_val")!!.toFloat()
            val desc = review.getString("desc")
            val date = review.getTimestamp("date")

            reviewList.add(Review(rid, user_id!!, user_name!!, user_img!!, rating_val, desc!!, date!!))

        }

        var avg = 0f
        for (r in reviewList){
            avg+= r.rating_val
        }
        var rVal = (avg/reviewList.size)

        FirebaseFirestore.getInstance().collection("/PlaceList/$cat_name/SubCategory/$subcat_id/Place")
            .document(place_id)
            .update(mapOf(
                "ratingValue" to rVal
            ))

        return Resource.Success("Success")
    }


}