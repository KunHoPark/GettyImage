package com.leo.gettyimage.ui.viewer

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.leo.gettyimage.data.repository.ImageDetailRepository

class PhotoViewModelFactory(private val repository: ImageDetailRepository, val imageId: String): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        return PhotoViewModel(repository, imageId) as T
    }

}

