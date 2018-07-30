package com.leo.gettyimage.data.local

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import java.io.Serializable


@Entity(tableName = "getty_image_table")
data class GettyImageEntity(

        /**
         *  PrimaryKey
         */
        @PrimaryKey @ColumnInfo(name = "id") val id: String,

        /**
         *  Title
         */
        @ColumnInfo(name = "title") val title: String,

        /**
         * Index
         */
        @ColumnInfo(name = "image_index") val imageIndex: Int?,

        /**
         *  thumbnail url
         */
        @ColumnInfo(name = "thumbnail_url") var thumbnailUrl: String,

        /**
         * Detail description.
         */
        @ColumnInfo(name = "description") val description: String,

        /**
         * Reference count
         */
        @ColumnInfo(name = "ref_count") val RefCount: String


) : Serializable

