package com.vmyan.myantrip.data

import androidx.lifecycle.MutableLiveData
import com.vmyan.myantrip.model.Word
import com.vmyan.myantrip.model.WordCategory
import com.vmyan.myantrip.utils.Resource

interface CommunicationRepository {
    suspend fun getCategory() : Resource<MutableList<WordCategory>>
    suspend fun getWordList() : Resource<MutableList<Word>>
    suspend fun getWordListFromD() : Resource<MutableList<Word>>
    suspend fun getWordByCategory(categoryId : String) : Resource<MutableList<Word>>
}