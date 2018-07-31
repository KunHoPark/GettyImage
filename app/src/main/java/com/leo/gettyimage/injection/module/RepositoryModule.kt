package com.leo.gettyimage.injection.module;


import com.leo.gettyimage.data.local.WalletRoomDatabase
import com.leo.gettyimage.data.remote.api.GettyRemoteApi
import com.leo.gettyimage.data.repository.GettyImageRepository
import com.leo.gettyimage.data.repository.ImageDetailRepository
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Named
import javax.inject.Singleton

@Module
class RepositoryModule {

    @Provides
    @Singleton
    fun provideGettyImageRepository(@Named("getty") restAdapter: Retrofit, walletRoomDatabase: WalletRoomDatabase): GettyImageRepository =
            GettyImageRepository(restAdapter.create(GettyRemoteApi::class.java), walletRoomDatabase.gettyImageDao())

    @Provides
    @Singleton
    fun provideImageDetailRepository(@Named("getty") restAdapter: Retrofit, walletRoomDatabase: WalletRoomDatabase): ImageDetailRepository =
            ImageDetailRepository(restAdapter.create(GettyRemoteApi::class.java), walletRoomDatabase.gettyImageDao())


}
