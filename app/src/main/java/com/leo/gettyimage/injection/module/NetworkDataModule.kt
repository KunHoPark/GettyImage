package com.leo.gettyimage.injection.module;

import com.leo.gettyimage.application.Constants
import com.leo.gettyimage.util.RetrofitLogger
import com.squareup.moshi.KotlinJsonAdapterFactory
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

/**
* NetworkDataModule
* @author JungWoongLee
* @since 2018. 6. 8. PM 1:32
**/
@Module
class NetworkDataModule {

    @Provides
    @Singleton
    fun providesMoshi(): Moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

    @Provides
    @Singleton
    fun provideRestAdapter(okHttpClient: OkHttpClient, moshi: Moshi): Retrofit {
        return Retrofit.Builder().client(okHttpClient).baseUrl(Constants.BASE_URL)
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
    }

    @Provides
    @Singleton
    fun providesOkHttpClient(): OkHttpClient {
        val builder = OkHttpClient.Builder().apply {
            readTimeout(Constants.READ_TIMEOUT_MILLISECONDS, TimeUnit.MILLISECONDS)
            writeTimeout(Constants.WRITE_TIMEOUT_MILLISECONDS, TimeUnit.MILLISECONDS)
            connectTimeout(Constants.CONNECT_TIMEOUT_MILLISECONDS, TimeUnit.MILLISECONDS)
            addInterceptor(HttpLoggingInterceptor(RetrofitLogger(Constants.HTTP_LOGGING_PRETTY_PRINTING_ENABLE)).apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
        }

        return builder.build()
    }

}
