package com.leo.gettyimage.injection.module;


import com.leo.gettyimage.data.local.WalletRoomDatabase
import com.leo.gettyimage.data.remote.api.ApiInterface
import com.leo.gettyimage.data.repository.GettyGalleryRepository
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
class RepositoryModule {

    @Provides
    @Singleton
    fun provideGettyGalleryRepository(restAdapter: Retrofit, walletRoomDatabase: WalletRoomDatabase): GettyGalleryRepository =
            GettyGalleryRepository(restAdapter.create(ApiInterface::class.java), walletRoomDatabase.gettyGalleryDao())

}
