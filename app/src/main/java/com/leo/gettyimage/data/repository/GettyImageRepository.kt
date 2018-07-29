package com.leo.gettyimage.data.repository

import com.konai.cryptokona.data.local.GettyImageDao
import com.konai.cryptokona.data.local.GettyImageEntity
import com.konai.cryptox.kotlin.extension.subStringTagId
import com.leo.gettyimage.application.GettyImageApp
import com.leo.gettyimage.data.remote.api.GettyRemoteApi
import com.leo.gettyimage.util.LeoLog
import com.leo.gettyimage.util.NetworkUtils
import io.reactivex.Flowable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import org.jsoup.Jsoup
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException


class GettyImageRepository(val remoteApi: GettyRemoteApi, val gettyImageDao: GettyImageDao) {
    internal val tag = this.javaClass.simpleName

    private val gettyImages = ArrayList<GettyImageEntity>()

    val isLoadingFromGettySite: BehaviorSubject<List<GettyImageEntity>> = BehaviorSubject.create()

    fun getCollections(){

        val stringCall = remoteApi.getCollections()
        stringCall.enqueue(object : Callback<String>{
            override fun onResponse(call: Call<String>?, response: Response<String>?) {
                LeoLog.i(tag, "getCollections onResponse response=$response")
                response?.let {
                    LeoLog.i(tag, "getCollections onResponse response.body=${it.body()}")
                    getCollections(it.body())
                            .subscribe{
                                isLoadingFromGettySite.onNext(it)
                            }
                }
            }

            override fun onFailure(call: Call<String>?, t: Throwable?) {
                LeoLog.e(tag, t!!.localizedMessage)
                isLoadingFromGettySite.onError(throw IOException("Load data fail"))
            }
        })
    }

    /**
     * Getty 사이트로부 Html 포멧을 파서 후 GettyImageEntity에 저장 한다.
     */
    private fun getCollections(response: String?) : Flowable<List<GettyImageEntity>> {

        return Flowable.just(response)
                .subscribeOn(Schedulers.io())
                .map {
                    return@map Jsoup.parse(response).select("div.gallery-item-caption p a")
                }
                .map {
                    it.forEach {
                        val id = it.attr("href").subStringTagId("id=")
                        val title = it.childNode(0).toString()

                        val item = GettyImageEntity(id, title, "", "")
                        gettyImages.add(item)
                    }
                    return@map gettyImages
                }
                .flatMap {
                    gettyImageDao.insertAll(it)
                    return@flatMap gettyImageDao.getGettyImagesRx().subscribeOn(Schedulers.io())
                }
    }


//    fun getCryptocurrencies(limit: Int, offset: Int): Observable<List<GettyGalleryData>> {
//        var observableFromApi: Observable<List<GettyGalleryData>>? = null
//
//        if (isLoadFromServer(offset)){
//            observableFromApi = getCryptocurrenciesFromApi()
//        }
//        val observableFromDb = getCryptocurrenciesFromDb(limit, offset)
//
//        return if (isLoadFromServer(offset)) {
//            Observable.concatArrayEager(observableFromApi, observableFromDb)
//        }else {
//            observableFromDb
//        }
//    }

//    private fun getCryptocurrenciesFromApi(): Observable<List<GettyGalleryData>> {
//        return apiInterface.getGettyGalleries(Constants.START_ZERO_VALUE)
//                .doOnNext {
//                    Log.e("REPOSITORY API * ", it.size.toString())
//                    for (item in it) {
//                        gettyGalleryDao.insertGettyGalleryData(item)
//                    }
//                }
//    }
//
//    private fun getCryptocurrenciesFromDb(limit: Int, offset: Int): Observable<List<GettyGalleryData>> {
//        return gettyGalleryDao.queryGettyGalleriesData(limit, offset)
//                .toObservable()
//                .doOnNext {
//                    //Print log it.size :)
//                    Log.e("REPOSITORY DB *** ", it.size.toString())
//                }
//    }

    private fun isLoadFromServer(offset: Int): Boolean{
        if (NetworkUtils.isNetworkAvailable(GettyImageApp.applicationContext()) && offset==0) {
            return true
        }
        return false
    }




}