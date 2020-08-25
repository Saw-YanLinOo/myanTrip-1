package com.vmyan.myantrip.data.booking.carRental.bookingCate

import com.vmyan.myantrip.model.bookingCate.BookingCateItem
import com.vmyan.myantrip.model.bookingCate.PromoSliderItem
import com.vmyan.myantrip.model.bookingCate.TownListItem
import com.vmyan.myantrip.utils.Resource

interface BookingCategoriesRepository{
    suspend fun getBookingCategories() : Resource<MutableList<BookingCateItem>>
    suspend fun getPromoSliderImage()  : Resource<MutableList<PromoSliderItem>>
    suspend fun getCityList()  :  Resource<MutableList<TownListItem>>
}