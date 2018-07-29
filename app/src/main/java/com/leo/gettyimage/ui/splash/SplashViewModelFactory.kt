package com.leo.gettyimage.ui.splash

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.leo.gettyimage.data.repository.GettyGalleryRepository

class SplashViewModelFactory(val mainRepository: GettyGalleryRepository): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        return SplashViewModel(mainRepository) as T
    }

}

