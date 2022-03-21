package com.switcherette.plantapp.data.repositories

import com.switcherette.plantapp.data.PlantInfo
import com.switcherette.plantapp.data.room.PlantInfoDao
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject


class PlantInfoRepository: KoinComponent {
    private val plantInfoDao : PlantInfoDao by inject()

    fun getPlantByName(name: String): PlantInfo?{
        return plantInfoDao.getPlantByName(name)
    }

    fun getAllPlants(): List<PlantInfo>{
        return plantInfoDao.getAllPlantInfo()
    }

}