package com.vmyan.myantrip.data.booking.carRental.bookingCate

import com.vmyan.myantrip.model.bookingCate.BookingCateItem
import com.vmyan.myantrip.model.bookingCate.PromoSliderItem
import com.vmyan.myantrip.model.bookingCate.TownListItem

interface BookingCategoriesRepository{
    suspend fun getBookingCategories() : com.vmyan.myantrip.utils.Resource<MutableList<BookingCateItem>>
    suspend fun getPromoSliderImage()  : com.vmyan.myantrip.utils.Resource<MutableList<PromoSliderItem>>
    suspend fun getCityList()  : com.vmyan.myantrip.utils.Resource<MutableList<TownListItem>>
}