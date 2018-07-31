package com.leo.gettyimage.injection.module;

import com.leo.gettyimage.application.Constants
import com.leo.gettyimage.util.RetrofitLogger
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

/**
 * NetworkDataModule
 * AppComponent에 연결 된다.
 * @author KunHoPark
 * @since 2018. 7. 30. PM 2:07
 **/
@Module
class NetworkDataModule {

    @Provides
    @Named("getty")
    @Singleton
    fun provideWebRestAdapter(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder().client(okHttpClient).baseUrl(Constants.GETTY_BASE_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
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
