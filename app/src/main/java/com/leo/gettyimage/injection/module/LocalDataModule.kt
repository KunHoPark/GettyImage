package com.leo.gettyimage.injection.module;

import android.app.Application
import android.arch.persistence.room.Room
import com.leo.gettyimage.data.local.WalletRoomDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
* LocalDataModule
 * AppComponent에 연결 된다.
* @author KunHoPark
* @since 2018. 7. 30. PM 2:07
**/
@Module
class LocalDataModule {

    @Provides
    @Singleton
    fun provideWalletDatabase(application : Application): WalletRoomDatabase
            = Room.databaseBuilder(application, WalletRoomDatabase::class.java, "crypto_kona_wallet.db")
                .allowMainThreadQueries()
                .build()





}
