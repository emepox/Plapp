package com.switcherette.plantapp.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.switcherette.plantapp.data.WaterEvent

@Dao
interface WaterDao {
    @Insert (onConflict = REPLACE)
    fun addNewWaterEvent(waterEvent: WaterEvent)

    @Query("SELECT * FROM WaterEvent")
    fun getAllWaterEvents(): List<WaterEvent>

    @Query("SELECT * FROM WaterEvent WHERE plantId LIKE (:plantId)")
    fun getWaterEventById(plantId: String): WaterEvent

    @Delete
    fun deleteWaterEvent(waterEvent: WaterEvent)
}