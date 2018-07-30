package com.konai.cryptokona.data.local

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey


@Entity(tableName = "getty_image_table")
class GettyImageEntity(

        /**
         *  PrimaryKey
         */
        @PrimaryKey @ColumnInfo(name = "id") var id: String,

        /**
         *  Title
         */
        @ColumnInfo(name = "title") var title: String,

        /**
         * Detail description.
         */
        @ColumnInfo(name = "description") var description: String,

        /**
         * Reference count
         */
        @ColumnInfo(name = "ref_count") var RefCount: String) {

}

