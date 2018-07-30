package com.leo.gettyimage.ui.ble

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.leo.gettyimage.data.repository.GettyGalleryRepository

/**
 * BleScanModule
 * @author KunHoPark
 * @since 2018. 7. 17. AM 10:43
 **/
class BleScanViewModelFactory(val mainRepository: GettyGalleryRepository): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        return BleScanViewModel(mainRepository) as T
    }

}

