package com.vmyan.myantrip.data

import com.vmyan.myantrip.model.hotel.BankCard
import com.vmyan.myantrip.utils.Resource


interface BankPaymentRepository{
    suspend fun getBankList() : Resource<MutableList<BankCard>>
}