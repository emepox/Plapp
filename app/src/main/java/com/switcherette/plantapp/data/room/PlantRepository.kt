package com.switcherette.plantapp.data.room

import com.switcherette.plantapp.data.UserPlant
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class PlantRepository: KoinComponent {
    private val plantDao : PlantDao by inject()

    fun addNewUserPlant(userPlant: UserPlant){
        plantDao.addNewUserPlant(userPlant)
    }

    fun getUserPlantsByUserId(userId: String): List<UserPlant>{
        return plantDao.getUserPlantsByUserId(userId)
    }

    fun deleteUserPlant(userPlant: UserPlant){
        plantDao.deleteUserPlant(userPlant)
    }
}