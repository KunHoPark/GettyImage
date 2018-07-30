package com.leo.gettyimage.ui.ble


import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.databinding.ObservableArrayList
import com.leo.gettyimage.data.local.GettyGalleryData
import com.leo.gettyimage.data.repository.GettyGalleryRepository
import com.leo.gettyimage.util.LeoLog
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit
import javax.inject.Inject

/**
 * BleScanViewModel
 * @author KunHoPark
 * @since 2018. 7. 17. AM 10:43
 **/
class BleScanViewModel
@Inject constructor(val mainRepository: GettyGalleryRepository) : ViewModel() {
    internal val tag = this.javaClass.simpleName

    val cryptocurrenciesResult= ObservableArrayList<GettyGalleryData>()
    var cryptocurrenciesError: MutableLiveData<String> = MutableLiveData()
    var cryptocurrenciesLoader: MutableLiveData<Boolean> = MutableLiveData()
    lateinit var disposableObserver: DisposableObserver<List<GettyGalleryData>>

    init {
//        GettyImageApp.appComponent.inject(this)
    }


//    fun cryptocurrenciesResult(): LiveData<List<GettyGalleryData>> {
//        return cryptocurrenciesResult
//    }

    fun cryptocurrenciesError(): LiveData<String> {
        return cryptocurrenciesError
    }

    fun cryptocurrenciesLoader(): LiveData<Boolean> {
        return cryptocurrenciesLoader
    }

    fun loadCryptocurrencies(limit: Int, offset: Int ) {
        LeoLog.i(tag, "loadCryptocurrencies limit=$limit , offset=$offset")

        disposableObserver = object : DisposableObserver<List<GettyGalleryData>>() {
            override fun onComplete() {

            }

            override fun onNext(cryptocurrencies: List<GettyGalleryData>) {
//                cryptocurrenciesResult.postValue(cryptocurrencies)
                LeoLog.i(tag, "loadCryptocurrencies onNext cryptocurrencies.size=${cryptocurrencies.size}")
                cryptocurrenciesResult.addAll(cryptocurrencies)
                LeoLog.i(tag, "loadCryptocurrencies onNext cryptocurrenciesResult.size=${cryptocurrenciesResult.size}")
                cryptocurrenciesLoader.postValue(false)
            }

            override fun onError(e: Throwable) {
                cryptocurrenciesError.postValue(e.message)
                cryptocurrenciesLoader.postValue(false)
            }
        }

        mainRepository.getCryptocurrencies(limit, offset)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .debounce(400, TimeUnit.MILLISECONDS)
                .subscribe(disposableObserver)
    }

    fun disposeElements(){
        if(null != disposableObserver && !disposableObserver.isDisposed) disposableObserver.dispose()
    }

}
