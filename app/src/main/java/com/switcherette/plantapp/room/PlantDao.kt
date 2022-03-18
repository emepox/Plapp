package com.switcherette.plantapp.room

import androidx.room.*
import com.switcherette.plantapp.data.UserPlant

@Dao
interface PlantDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addNewUserPlant(userPlant: UserPlant): Long

    @Query("SELECT * FROM UserPlant WHERE userId LIKE (:userId)")
    fun getUserPlantsByUserId(userId: String): List<UserPlant>

    @Delete
    fun deleteUserPlant(userPlant: UserPlant)
}