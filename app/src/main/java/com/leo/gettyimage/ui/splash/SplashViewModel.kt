package com.leo.gettyimage.ui.splash


import android.arch.lifecycle.ViewModel
import com.leo.gettyimage.data.repository.GettyGalleryRepository
import javax.inject.Inject

class SplashViewModel
@Inject constructor(val mainRepository: GettyGalleryRepository) : ViewModel() {
    internal val tag = this.javaClass.simpleName


    init {
//        GettyImageApp.appComponent.inject(this)
    }


}
