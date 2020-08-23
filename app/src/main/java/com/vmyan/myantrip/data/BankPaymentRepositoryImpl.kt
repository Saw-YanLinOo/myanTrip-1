package com.vmyan.myantrip.data

import com.google.firebase.firestore.FirebaseFirestore
import com.vmyan.myantrip.model.hotel.BankCard
import com.vmyan.myantrip.utils.Resource
import kotlinx.coroutines.tasks.await

class BankPaymentRepositoryImpl : BankPaymentRepository {
    override suspend fun getBankList(): Resource<MutableList<BankCard>> {
            val bankList = mutableListOf <BankCard>()
            val resultList= FirebaseFirestore.getInstance()
                .collection("BankList")
                .get()
                .await()
            for (document in resultList){
                val bankId = document.id
                val bankName = document.getString("BankName")
                val bankLogo = document.getString("BankLogo")
                val bankType = document.getString("BankTypeImg")
                println(bankName)
                println(bankLogo)
                println(bankType)
                bankList.add(BankCard(bankId,bankName!!, bankLogo!!,bankType!!))
            }

            return Resource.Success(bankList)


    }

}