package com.vmyan.myantrip.ui.viewmodel

import androidx.lifecycle.liveData
import com.vmyan.myantrip.data.CommunicationRepository
import com.vmyan.myantrip.utils.Resource
import kotlinx.coroutines.Dispatchers
import java.lang.Exception

class CommunicationViewModel(private val communicationRepository: CommunicationRepository) {

    fun getCategory() = liveData(Dispatchers.IO) {
        emit(Resource.Loading())
        try {
            val category = communicationRepository.getCategory()
            emit(category)
        }catch (e : Exception){
            emit(Resource.Failure(e.message.toString()))
        }
    }

    fun getWord() = liveData(Dispatchers.IO) {
        emit(Resource.Loading())
        try {
            val word = communicationRepository.getWordList()
            emit(word)
        }catch (e : Exception){
            emit(Resource.Failure(e.message.toString()))
        }
    }
    fun getWordByCategory(category_id : String) = liveData(Dispatchers.IO) {
        emit(Resource.Loading())
        try {
            val word = communicationRepository.getWordByCategory(category_id)
            emit(word)
        }catch (e : Exception){
            emit(Resource.Failure(e.message.toString()))
        }
    }

    fun getWordFromD() = liveData(Dispatchers.IO) {
        emit(Resource.Loading())
        try {
            val word = communicationRepository.getWordListFromD()
            emit(word)
        }catch (e : Exception){
            emit(Resource.Failure(e.message.toString()))
        }
    }
}