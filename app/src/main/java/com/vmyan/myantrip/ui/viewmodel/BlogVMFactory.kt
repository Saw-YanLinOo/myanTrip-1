package com.vmyan.myantrip.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.vmyan.myantrip.data.BlogRepository

class BlogVMFactory(private val blogRepository : BlogRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(BlogRepository::class.java).newInstance(blogRepository)
    }
}