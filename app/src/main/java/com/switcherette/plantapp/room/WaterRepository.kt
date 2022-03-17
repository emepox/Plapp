package com.switcherette.plantapp.room

import com.switcherette.plantapp.data.WaterEvent
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class WaterRepository : KoinComponent {
    private val waterDB : WaterDBConnection by inject()

    fun addNewWaterEvent(waterEvent: WaterEvent){
        waterDB.instance.waterDao().addNewWaterEvent(waterEvent)
    }

    fun getAllWaterEvents(): List<WaterEvent>{
        return waterDB.instance.waterDao().getAllWaterEvents()
    }

    fun getWaterEventById(plantId: String): WaterEvent{
        return waterDB.instance.waterDao().getWaterEventById(plantId)
    }

    fun deleteWaterEvent(waterEvent: WaterEvent){
        waterDB.instance.waterDao().deleteWaterEvent(waterEvent)
    }
}