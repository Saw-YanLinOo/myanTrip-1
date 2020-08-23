package com.vmyan.myantrip.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.vmyan.myantrip.data.BankPaymentRepository

class BankVMFactory (private val bankPaymentRepository: BankPaymentRepository) : ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(BankPaymentRepository ::class.java).newInstance(bankPaymentRepository)
    }

}