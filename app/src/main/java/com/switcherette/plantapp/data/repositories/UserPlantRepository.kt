package com.switcherette.plantapp.data.repositories

import com.switcherette.plantapp.data.UserPlant
import com.switcherette.plantapp.data.room.UserPlantDao
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class UserPlantRepository : KoinComponent {
    private val userPlantDao: UserPlantDao by inject()

    fun addNewUserPlant(userPlant: UserPlant): Long {
        return userPlantDao.addNewUserPlant(userPlant)
    }

    fun getUserPlantsByUserId(userId: String): List<UserPlant> {
        return userPlantDao.getUserPlantsByUserId(userId)
    }

    fun getUserPlantByPlantId(plantId: String): UserPlant {
        return userPlantDao.getUserPlantsByPlantId(plantId)
    }

    fun deleteUserPlant(userPlant: UserPlant) {
        userPlantDao.deleteUserPlant(userPlant)
    }
}