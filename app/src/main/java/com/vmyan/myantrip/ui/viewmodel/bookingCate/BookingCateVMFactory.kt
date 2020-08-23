package com.vmyan.myantrip.ui.viewmodel.bookingCate

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.vmyan.myantrip.data.booking.carRental.bookingCate.BookingCategoriesRepository

class BookingCateVMFactory (private val bookingCategoriesRepository: BookingCategoriesRepository) :
    ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(BookingCategoriesRepository::class.java).newInstance(bookingCategoriesRepository)
    }
}