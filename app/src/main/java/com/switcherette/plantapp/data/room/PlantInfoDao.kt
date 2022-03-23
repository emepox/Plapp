package com.switcherette.plantapp.data.room

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import com.switcherette.plantapp.data.PlantInfo

@Dao
interface PlantInfoDao {
    @Query("SELECT * FROM PlantInfo")
    fun getAllPlantInfo(): List<PlantInfo>

    @Query("SELECT * FROM PlantInfo")
    fun getPagedPlantInfo(): PagingSource<Int, PlantInfo>

    @Query("SELECT * FROM PlantInfo WHERE scientificName LIKE (:name)")
    fun getPlantByName(name: String): PlantInfo?

    @Query("SELECT * FROM PlantInfo WHERE scientificName LIKE (:name)")
    fun getPagedPlantByName(name: String): PagingSource<Int, PlantInfo>
}
