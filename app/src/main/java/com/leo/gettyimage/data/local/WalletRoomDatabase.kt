package com.leo.gettyimage.data.local

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase

@Database(entities = [
    GettyImageEntity::class]
        , version = 1)

abstract class WalletRoomDatabase : RoomDatabase() {

    abstract fun gettyImageDao(): GettyImageDao

}
