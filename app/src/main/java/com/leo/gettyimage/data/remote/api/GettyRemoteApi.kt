package com.leo.gettyimage.data.remote.api


import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


interface GettyRemoteApi {

    @GET("collections/archive/slim-aarons.aspx")
    fun getCollections(): Call<String>

    @GET("picture-library/image.aspx")
    fun getImageDetail(@Query("id") id: String): Call<String>


}