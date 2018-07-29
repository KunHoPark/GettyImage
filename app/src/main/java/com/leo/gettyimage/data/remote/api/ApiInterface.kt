package com.leo.gettyimage.data.remote.api


import com.leo.gettyimage.data.local.GettyGalleryData
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query


interface ApiInterface {

    @GET("ticker/")
    fun getGettyGalleries(@Query("start") start: String): Observable<List<GettyGalleryData>>


}