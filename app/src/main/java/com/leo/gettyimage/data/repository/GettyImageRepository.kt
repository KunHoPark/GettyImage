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


class GettyImageRepository(private val remoteApi: GettyRemoteApi, private val gettyImageDao: GettyImageDao) {
    internal val tag = this.javaClass.simpleName

    private val gettyImages = ArrayList<GettyImageEntity>()

    // Respsitory에서 처리된 결과를 ViewModel로 알려 주식 위한 Observer.
    val isLoadingFromGettySite: BehaviorSubject<List<GettyImageEntity>> = BehaviorSubject.create()

    fun getCollections(isLoad: Boolean){

        when(isLoad){
            true -> {
                getGettyImagesFromServer()
            }
            else -> {
                // DB에서 정보를 가져 옴. 만약 DB에 정보가 없을 경우 서버를 통해 데이타를 가져 온다.
                if (gettyImageDao.getGettyImages().isNotEmpty()) {
                    getGettyImagesFromDB()
                }else{
                    getGettyImagesFromServer()
                }
            }
        }


    }

    /**
     * Getty 서버로 부터 Html 정보 가져 온 후 DB에 저장 하기 위해 parserHtmlAndSave 를 호출 한다.
     */
    private fun getGettyImagesFromServer(){

        if (isNetworkAvailAble()){
            val stringCall = remoteApi.getCollections()
            stringCall.enqueue(object : Callback<String>{
                override fun onResponse(call: Call<String>?, response: Response<String>?) {
                    LeoLog.i(tag, "getCollections onResponse response=$response")
                    response?.let {
                        LeoLog.i(tag, "getCollections onResponse response.body=${it.body()}")
                        parserHtmlAndSave(it.body())
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
    }

    /**
     * Getty 사이트로부 Html 포멧을 파서 후 GettyImageEntity에 저장 한다.
     */
    private fun parserHtmlAndSave(response: String?) : Flowable<List<GettyImageEntity>> {

        return Flowable.just(response)
                .subscribeOn(Schedulers.io())
                .map {
                    return@map Jsoup.parse(response).select("div.gallery-item-caption p a")
                }
                .map {
                    it.forEach {
                        val id = it.attr("href").subStringTagId("id=")
                        val title = it.childNode(0).toString()
                        GettyImageEntity(id, title, "", "").apply {
                            gettyImages.add(this)
                        }
                    }
                    return@map gettyImages
                }
                .map {
                    gettyImageDao.replaceAll(it)
                }
                .flatMap {
                    return@flatMap gettyImageDao.getGettyImagesRx().subscribeOn(Schedulers.io())
                }
    }

    /**
     * DB(getty_image_table)로 부터 GettyImageEntity 정보 가져 오기
     */
    private fun getGettyImagesFromDB(){
        gettyImageDao.getGettyImagesRx()
                .subscribeOn(Schedulers.io())
                .subscribe{
                    when(it.isNotEmpty()){
                        true -> {
                            isLoadingFromGettySite.onNext(it)
                        }
                        else -> {
                            isLoadingFromGettySite.onError(throw IOException("Load data fail"))
                        }
                    }

                }
    }

    /**
     * 네트워크 연결 상태 확인. 만약 미 연결되어 있으면 Exception 처리 한다.
     */
    private fun isNetworkAvailAble(): Boolean{
        if (NetworkUtils.isNetworkAvailable(GettyImageApp.applicationContext())) {
            return true
        }else{
            isLoadingFromGettySite.onError(throw IOException("Network connection fail"))
        }

        return false
    }




}