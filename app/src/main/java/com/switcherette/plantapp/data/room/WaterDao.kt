package com.switcherette.plantapp.data.room

import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE
import com.switcherette.plantapp.data.WaterEvent

@Dao
interface WaterDao {
    @Insert (onConflict = REPLACE)
    fun addNewWaterEvent(waterEvent: WaterEvent)

    @Query("SELECT * FROM WaterEvent")
    fun getAllWaterEvents(): List<WaterEvent>

    @Query("SELECT * FROM WaterEvent WHERE plantId LIKE (:plantId)")
    fun getWaterEventByPlantId(plantId: String): WaterEvent

    @Query("SELECT * FROM WaterEvent WHERE repeatStart LIKE (:startDate) ")
    fun getWaterEventByDate(startDate: Long): List<WaterEvent>

    @Query("SELECT * FROM WaterEvent WHERE repeatStart BETWEEN :startTime AND :endTime")
    fun getWaterEventByTimeRange(startTime: Long, endTime: Long): List<WaterEvent>

    @Query("SELECT * FROM WaterEvent ORDER BY repeatStart ASC LIMIT 1")
    fun getFirstWaterEventByDate(): WaterEvent

    @Query("SELECT repeatStart AND repeatInterval FROM WaterEvent WHERE plantId LIKE (:plantId)")
    fun getRepeatsByPlantId(plantId: String) : List<Long>

    @Update
    fun updateDates(list: List<WaterEvent>)

    @Delete
    fun deleteWaterEvent(waterEvent: WaterEvent)
}