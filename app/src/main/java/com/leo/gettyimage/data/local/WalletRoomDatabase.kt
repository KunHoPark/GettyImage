package com.leo.gettyimage.data.local

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.konai.cryptokona.data.local.GettyImageDao
import com.konai.cryptokona.data.local.GettyImageEntity

@Database(entities = [
    GettyImageEntity::class,
    GettyGalleryData::class]
        , version = 1)

abstract class WalletRoomDatabase : RoomDatabase() {

    abstract fun gettyImageDao(): GettyImageDao
    abstract fun gettyGalleryDao(): GettyGalleryDao

}
