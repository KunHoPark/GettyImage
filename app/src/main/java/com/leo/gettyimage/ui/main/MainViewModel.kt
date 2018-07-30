package com.leo.gettyimage.ui.main


import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.databinding.ObservableArrayList
import com.leo.gettyimage.data.local.GettyImageEntity
import com.leo.gettyimage.data.repository.GettyImageRepository
import com.leo.gettyimage.util.LeoLog
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class MainViewModel
@Inject constructor(val mainRepository: GettyImageRepository) : ViewModel() {
    internal val tag = this.javaClass.simpleName

    private var compositeDisposable = CompositeDisposable()
    var isLoadingSuccess: MutableLiveData<Boolean> = MutableLiveData()

    val gettyImages= ObservableArrayList<GettyImageEntity>()

    init {
//        GettyImageApp.appComponent.inject(this)
    }

    fun loadCollections() {
        mainRepository.getCollections(true)                 //Getty site로 부터 파서 후 이미지 정보 가져 오기.
        mainRepository.isLoadingFromGettySite                      //가져온 이미지 정보를 DB에 저장 하기.
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
                .also {
                    compositeDisposable.add(it)
                }
    }

    fun loadCollections(limit: Int, offset: Int) {
        LeoLog.i(tag, "loadCryptocurrencies limit=$limit , offset=$offset")
        mainRepository.getCollectionsFromDb(limit, offset)
                .subscribeOn(Schedulers.io())
                .subscribe(
                        {
                            isLoadingSuccess.postValue(true)
                            gettyImages += it
                        },
                        {
                            isLoadingSuccess.postValue(false)
                            LeoLog.e(tag, it.localizedMessage)
                        }
                )
                .also {
                    compositeDisposable.add(it)
                }
    }

    fun disposeElements() {
        compositeDisposable.clear()
    }

}