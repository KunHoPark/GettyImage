package com.leo.gettyimage.data.local

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import io.reactivex.Single

@Dao
interface GettyGalleryDao {

  @Query("SELECT * FROM cryptocurrencies ORDER BY rank limit :limit offset :offset")
  fun queryGettyGalleriesData(limit:Int, offset:Int): Single<List<GettyGalleryData>>

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  fun insertGettyGalleryData(cryptocurrency: GettyGalleryData)

  @Insert(
      onConflict = OnConflictStrategy.REPLACE
  )
  fun insertAllGettyGalleriesData(cryptocurrencies: List<GettyGalleryData>)
}