package com.switcherette.plantapp.data.repositories

import com.switcherette.plantapp.data.WaterEvent
import com.switcherette.plantapp.data.room.WaterDao
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class WaterRepository : KoinComponent {
    private val waterDao : WaterDao by inject()

    fun addNewWaterEvent(waterEvent: WaterEvent){
        waterDao.addNewWaterEvent(waterEvent)
    }

    fun getAllWaterEvents(): List<WaterEvent>{
        return waterDao.getAllWaterEvents()
    }

    fun getWaterEventByPlantId(plantId: String): WaterEvent{
        return waterDao.getWaterEventByPlantId(plantId)
    }

    fun getWaterEventByDate(startDate: Long): List<WaterEvent>{
        return waterDao.getWaterEventByDate(startDate)
    }

    fun getWaterEventByTimeRange(startTime: Long, endTime: Long): List<WaterEvent>{
        return waterDao.getWaterEventByTimeRange(startTime, endTime)
    }

    fun getFirstWaterEventByDate(): WaterEvent?{
        return  waterDao.getFirstWaterEventByDate()
    }

    fun updateDates(list: List<WaterEvent>){
        waterDao.updateDates(list)
    }

    fun deleteWaterEvent(waterEvent: WaterEvent){
        waterDao.deleteWaterEvent(waterEvent)
    }

}