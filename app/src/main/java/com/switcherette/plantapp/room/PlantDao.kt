package com.switcherette.plantapp.room

import androidx.room.*
import com.switcherette.plantapp.data.UserPlant
import com.switcherette.plantapp.data.WaterEvent

@Dao
interface PlantDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addNewUserPlant(userPlant: UserPlant)

    @Query("SELECT * FROM UserPlant WHERE userId LIKE (:userId)")
    fun getUserPlantsByUserId(userId: String): List<UserPlant>

    @Delete
    fun deleteUserPlant(userPlant: UserPlant)
}