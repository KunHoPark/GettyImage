package com.leo.gettyimage.ui.main


import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.databinding.ObservableArrayList
import com.leo.gettyimage.callback.OnLoadListener
import com.leo.gettyimage.data.local.GettyImageEntity
import com.leo.gettyimage.data.repository.GettyImageRepository
import com.leo.gettyimage.util.LeoLog
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class MainViewModel
@Inject constructor(private val gettyImageRepository: GettyImageRepository) : ViewModel() {
    internal val tag = this.javaClass.simpleName

    private var compositeDisposable = CompositeDisposable()
    var isLoadingSuccess: MutableLiveData<Boolean> = MutableLiveData()

    val gettyImages= ObservableArrayList<GettyImageEntity>()

    fun loadCollections() {
        gettyImageRepository.getCollections(true, object: OnLoadListener{
            override fun onSuccess(gettyImageEntity: List<GettyImageEntity>?) {
                isLoadingSuccess.postValue(true)
            }

            override fun onFail(error: String?) {
                isLoadingSuccess.postValue(false)
                LeoLog.e(tag, error!!)
            }
        })                 //Getty site로 부터 파서 후 이미지 정보 가져 오기.
    }

    fun loadCollections(limit: Int, offset: Int, isOverWrite: Boolean) {
        LeoLog.i(tag, "loadCryptocurrencies limit=$limit , offset=$offset")
        gettyImageRepository.getCollectionsFromDb(limit, offset)
                .subscribeOn(Schedulers.io())
                .subscribe(
                        {
                            isLoadingSuccess.postValue(true)
                            when(isOverWrite){
                                true -> {
                                    gettyImages.removeAll(gettyImages)
                                    gettyImages.addAll(it)
                                }
                                else -> gettyImages += it
                            }
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
