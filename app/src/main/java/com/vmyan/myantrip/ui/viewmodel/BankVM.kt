package com.vmyan.myantrip.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.vmyan.myantrip.data.BankPaymentRepository
import com.vmyan.myantrip.utils.Resource
import kotlinx.coroutines.Dispatchers

class BankVM (private val bankPaymentRepository: BankPaymentRepository) : ViewModel() {
    fun fetchBankList() = liveData(Dispatchers.IO) {
        emit(Resource.Loading())
        try {
            val carList = bankPaymentRepository.getBankList()
            emit(carList)
        } catch (e: Exception) {
            emit(Resource.Failure(e.message!!))
        }

    }
}