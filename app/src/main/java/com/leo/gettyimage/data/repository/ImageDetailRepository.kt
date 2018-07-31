package com.leo.gettyimage.data.repository

import com.leo.gettyimage.application.GettyImageApp
import com.leo.gettyimage.callback.OnLoadListener
import com.leo.gettyimage.data.local.GettyImageDao
import com.leo.gettyimage.data.local.GettyImageEntity
import com.leo.gettyimage.data.remote.api.GettyRemoteApi
import com.leo.gettyimage.util.LeoLog
import com.leo.gettyimage.util.NetworkUtils
import io.reactivex.Flowable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import org.jsoup.Jsoup
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ImageDetailRepository(private val remoteApi: GettyRemoteApi, private val gettyImageDao: GettyImageDao) {
    internal val tag = this.javaClass.simpleName
    var compositeDisposable = CompositeDisposable()

    // Respsitory에서 처리된 결과를 ViewModel로 알려 주기 위한 리스너.
    lateinit var onLoadListener: OnLoadListener

    // DB에 내용을 리턴 한다. 서버에 정보를 가져 온 후 DB에 갱신 한다.
    fun getGettyImage(id: String, onLoadListener: OnLoadListener): GettyImageEntity {
        this.onLoadListener = onLoadListener

        getImageDetailFromServer(id)
        return gettyImageDao.getGettyImageByCoinIndex(id.toInt())
    }

    /**
     * Getty 서버로 부터 Html 정보 가져 온 후 DB에 저장 하기 위해 parserHtmlAndSave 를 호출 한다.
     */
    private fun getImageDetailFromServer(id: String) {

        if (isNetworkAvailAble()) {
            val stringCall = remoteApi.getImageDetail(id)
            stringCall.enqueue(object : Callback<String> {
                override fun onResponse(call: Call<String>?, response: Response<String>?) {
                    LeoLog.i(tag, "getCollections onResponse response=$response")
                    response?.let {
                        LeoLog.i(tag, "getCollections onResponse response.body=${it.body()}")
                        parserHtmlAndSave(it.body(), id)
                                .subscribeOn(Schedulers.io())
                                .subscribe {
                                    val gettyImages = ArrayList<GettyImageEntity>()
                                    gettyImages.add(it)
                                    onLoadListener.onSuccess(gettyImages)
                                }
                                .apply {
                                    compositeDisposable.add(this)
                                }
                    }
                }

                override fun onFailure(call: Call<String>?, t: Throwable?) {
                    onLoadListener.onFail("Load data fail")
                }
            })
        }
    }

    /**
     * Getty 사이트로부 Html 포멧을 파서 후 GettyImageEntity에 저장 한다.
     */
    private fun parserHtmlAndSave(response: String?, id: String): Flowable<GettyImageEntity> {

        return Flowable.just(response)
                .map {
                    val elements = Jsoup.parse(response).select("div[class=innerImageArea]")
                    elements?.let {
                        val originalImgUrl =it.select("img").attr("src")
                        val gettyImgeEntity = gettyImageDao.getGettyImageByCoinIndex(id.toInt())
                        gettyImgeEntity.originalImgUrl = originalImgUrl

                        return@map gettyImgeEntity
                    }
                }
                .map { gettyImgeEntity ->
                    val elements = Jsoup.parse(response).select("div[class=proddetails]")
                    elements?.let {
                        if (it.select("p")[0].childNodeSize()>0){
                            val refCount =it.select("p")[0].childNode(0).toString()
                            gettyImgeEntity.refCount = refCount
                        }
                        if (it.select("p")[1].childNodeSize()>0){
                            val description =it.select("p")[1].childNode(0).toString()
                            gettyImgeEntity.description = description
                        }

                        return@map gettyImgeEntity
                    }
                }
                .flatMap {
                    gettyImageDao.update(it)
                    return@flatMap gettyImageDao.getGettyImageRxByCoinIndex(id.toInt())
                }
    }

    /**
     * 네트워크 연결 상태 확인. 만약 미 연결되어 있으면 Exception 처리 한다.
     */
    private fun isNetworkAvailAble(): Boolean {
        if (NetworkUtils.isNetworkAvailable(GettyImageApp.applicationContext())) {
            return true
        } else {
            onLoadListener.onFail("Network connection fail")
        }

        return false
    }


}