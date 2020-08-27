package com.vmyan.myantrip.data.booking.carRental.bookingCate

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.vmyan.myantrip.model.bookingCate.BookingCateItem
import com.vmyan.myantrip.model.bookingCate.PromoSliderItem
import com.vmyan.myantrip.model.bookingCate.TownListItem
import com.vmyan.myantrip.utils.Resource
import kotlinx.coroutines.tasks.await

class BookingCategoriesRepositoryImpl : BookingCategoriesRepository {
    override suspend fun getBookingCategories(): Resource<MutableList<BookingCateItem>> {
        val cateList = mutableListOf <BookingCateItem>()
        val resultList=FirebaseFirestore.getInstance()
            .collection("BookingCategories").orderBy("bookingCate_name",Query.Direction.ASCENDING)
            .get()
            .await()
        for (document in resultList){
            val cat_id = document.id
            val cat_name = document.getString("bookingCate_name")
            val cat_img_url = document.getString("bookingCate_imgUrl")
            cateList.add(BookingCateItem(cat_id,cat_name!!, cat_img_url!!))
        }

        return Resource.Success(cateList)

    }

    override suspend fun getPromoSliderImage(): Resource<MutableList<PromoSliderItem>> {
        val promoImageList = mutableListOf<PromoSliderItem>()
        val resultPromoImageList=FirebaseFirestore.getInstance()
            .collection("PromoImage")
            .get()
            .await()
        for (promo in resultPromoImageList){
            val id = promo.id
            val imgUrl = promo.get("imgUrl") as ArrayList<String>
            promoImageList.add(PromoSliderItem(id,imgUrl))
        }
        return Resource.Success(promoImageList)


    }

    override suspend fun getCityList(): Resource<MutableList<TownListItem>> {
        val cityList = mutableListOf<TownListItem>()
        val resultList=FirebaseFirestore.getInstance()
            .collection("/BookingCategories/0howcfkVUqShkvPlTZqg/CityName")
            .get()
            .await()
        for (city in resultList){
            val town_Id = city.id
            val townName = city.getString("city_Name")
            val townImageUrl=city.getString("city_ImageUrl")
            cityList.add(TownListItem(town_Id,townName !!,townImageUrl!!))
        }
        return Resource.Success(cityList)

    }
}