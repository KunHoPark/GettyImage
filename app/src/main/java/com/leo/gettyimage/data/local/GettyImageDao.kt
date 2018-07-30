package com.leo.gettyimage.data.local

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*
import io.reactivex.Flowable

@Dao
interface GettyImageDao {

    @Query("SELECT * FROM getty_image_table")
    fun getLiveGettyImages(): LiveData<List<GettyImageEntity>>

    @Query("SELECT * FROM getty_image_table")
    fun getGettyImages(): List<GettyImageEntity>

    @Query("SELECT * FROM getty_image_table")
    fun getGettyImagesRx(): Flowable<List<GettyImageEntity>>

    @Query("SELECT * FROM getty_image_table WHERE id = (:id)")
    fun getGettyImageByCoinIndex(id: Int): GettyImageEntity

    @Query("SELECT * FROM getty_image_table WHERE id = (:id)")
    fun getGettyImageRxByCoinIndex(id: Int): Flowable<GettyImageEntity>

    @Query("SELECT * FROM getty_image_table WHERE id = (:id)")
    fun getLiveGettyImageByCoinIndex(id: Int): LiveData<GettyImageEntity>

    @Query("SELECT * FROM getty_image_table ORDER BY image_index limit :limit offset :offset")
    fun queryGettyImagesRx(limit:Int, offset:Int): Flowable<List<GettyImageEntity>>

    @Insert
    fun insert(coinWalletEntity: GettyImageEntity)

    @Insert
    fun insertAll(coinWalletEntities: List<GettyImageEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun replaceAll(gettyImages: List<GettyImageEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun replace(gettyImage: GettyImageEntity)

    @Delete
    fun delete(coinWalletEntity: GettyImageEntity)

    @Query("DELETE FROM getty_image_table")
    fun deleteAll()

    @Update
    fun update(coinWalletEntity: GettyImageEntity)

}
