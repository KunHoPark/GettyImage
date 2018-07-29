package com.leo.gettyimage.injection.module;


import com.leo.gettyimage.data.local.WalletRoomDatabase
import com.leo.gettyimage.data.remote.api.ApiInterface
import com.leo.gettyimage.data.remote.api.GettyRemoteApi
import com.leo.gettyimage.data.repository.GettyGalleryRepository
import com.leo.gettyimage.data.repository.GettyImageRepository
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Named
import javax.inject.Singleton

@Module
class RepositoryModule {

    @Provides
    @Singleton
    fun provideGettyGalleryRepository(@Named("moshi") restAdapter: Retrofit, walletRoomDatabase: WalletRoomDatabase): GettyGalleryRepository =
            GettyGalleryRepository(restAdapter.create(ApiInterface::class.java), walletRoomDatabase.gettyGalleryDao())

    @Provides
    @Singleton
    fun provideGettyImageRepository(@Named("getty") restAdapter: Retrofit, walletRoomDatabase: WalletRoomDatabase): GettyImageRepository =
            GettyImageRepository(restAdapter.create(GettyRemoteApi::class.java), walletRoomDatabase.gettyImageDao())


}
