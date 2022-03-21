package com.switcherette.plantapp.data.room

import androidx.room.Dao
import androidx.room.Query
import com.switcherette.plantapp.data.PlantInfo

@Dao
interface PlantInfoDao {
    @Query("SELECT * FROM PlantInfo")
    fun getAllPlantInfo(): List<PlantInfo>
}