package com.konai.cryptokona.data.local

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

    @Insert
    fun insert(coinWalletEntity: GettyImageEntity)

    @Insert
    fun insertAll(coinWalletEntities: List<GettyImageEntity>)

    @Delete
    fun delete(coinWalletEntity: GettyImageEntity)

    @Query("DELETE FROM getty_image_table")
    fun deleteAll()

    @Update
    fun update(coinWalletEntity: GettyImageEntity)

}
