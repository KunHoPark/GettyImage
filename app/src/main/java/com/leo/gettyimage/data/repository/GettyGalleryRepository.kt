package com.leo.gettyimage.data.repository

import android.util.Log
import com.leo.gettyimage.application.Constants
import com.leo.gettyimage.application.GettyImageApp
import com.leo.gettyimage.data.local.GettyGalleryDao
import com.leo.gettyimage.data.local.GettyGalleryData
import com.leo.gettyimage.data.remote.api.ApiInterface
import com.leo.gettyimage.util.NetworkUtils
import io.reactivex.Observable


class GettyGalleryRepository(val apiInterface: ApiInterface, val gettyGalleryDao: GettyGalleryDao) {

    fun getCryptocurrencies(limit: Int, offset: Int): Observable<List<GettyGalleryData>> {
        var observableFromApi: Observable<List<GettyGalleryData>>? = null

        if (isLoadFromServer(offset)){
            observableFromApi = getCryptocurrenciesFromApi()
        }
        val observableFromDb = getCryptocurrenciesFromDb(limit, offset)

        return if (isLoadFromServer(offset)) {
            Observable.concatArrayEager(observableFromApi, observableFromDb)
        }else {
            observableFromDb
        }
    }

    private fun getCryptocurrenciesFromApi(): Observable<List<GettyGalleryData>> {
        return apiInterface.getGettyGalleries(Constants.START_ZERO_VALUE)
                .doOnNext {
                    Log.e("REPOSITORY API * ", it.size.toString())
                    for (item in it) {
                        gettyGalleryDao.insertGettyGalleryData(item)
                    }
                }
    }

    private fun getCryptocurrenciesFromDb(limit: Int, offset: Int): Observable<List<GettyGalleryData>> {
        return gettyGalleryDao.queryGettyGalleriesData(limit, offset)
                .toObservable()
                .doOnNext {
                    //Print log it.size :)
                    Log.e("REPOSITORY DB *** ", it.size.toString())
                }
    }

    private fun isLoadFromServer(offset: Int): Boolean{
        if (NetworkUtils.isNetworkAvailable(GettyImageApp.applicationContext()) && offset==0) {
            return true
        }
        return false
    }




}