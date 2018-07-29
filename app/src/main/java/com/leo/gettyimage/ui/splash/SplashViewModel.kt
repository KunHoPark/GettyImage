package com.leo.gettyimage.ui.splash


import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.leo.gettyimage.data.repository.GettyImageRepository
import com.leo.gettyimage.util.LeoLog
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class SplashViewModel
@Inject constructor(val mainRepository: GettyImageRepository) : ViewModel() {
    internal val tag = this.javaClass.simpleName

    var isLoadingSuccess: MutableLiveData<Boolean> = MutableLiveData()

    init {
//        GettyImageApp.appComponent.inject(this)
    }

    fun loadCollections(){
        mainRepository.getCollections()
        mainRepository.isLoadingFromGettySite
                .subscribeOn(Schedulers.io())
                .subscribe(
                        {
                            isLoadingSuccess.postValue(true)
                        },
                        {
                            isLoadingSuccess.postValue(false)
                            LeoLog.e(tag, it.localizedMessage)
                        }
                )
    }

}
