package com.leo.gettyimage.data.remote.api


import retrofit2.Call
import retrofit2.http.GET


interface GettyRemoteApi {

    @GET("collections/archive/slim-aarons.aspx")
    fun getCollections(): Call<String>


}