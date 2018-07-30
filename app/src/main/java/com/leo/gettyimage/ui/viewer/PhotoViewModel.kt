package com.leo.gettyimage.ui.viewer


import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.databinding.ObservableField
import com.leo.gettyimage.data.local.GettyImageEntity
import com.leo.gettyimage.data.repository.ImageDetailRepository
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class PhotoViewModel
@Inject constructor(val imageDetailRepository: ImageDetailRepository, private val imageId: String) : ViewModel() {
    internal val tag = this.javaClass.simpleName

    private var compositeDisposable = CompositeDisposable()
    var isLoadingSuccess: MutableLiveData<Boolean> = MutableLiveData()

    var gettyImage= ObservableField<GettyImageEntity>()


    init {
//        GettyImageApp.appComponent.inject(this)
    }

    fun loadGettyImage() {
        gettyImage.set(imageDetailRepository.getGettyImage(imageId))
    }


    fun disposeElements() {
        compositeDisposable.clear()
    }


}
