package com.leo.gettyimage.ui.main

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.leo.gettyimage.data.repository.GettyImageRepository

class MainViewModelFactory(private val mainRepository: GettyImageRepository): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        return MainViewModel(mainRepository) as T
    }

}

