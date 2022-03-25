package com.switcherette.plantapp.data.room

import androidx.room.*
import com.switcherette.plantapp.data.UserPlant

@Dao
interface UserPlantDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addNewUserPlant(userPlant: UserPlant): Long

    @Query("SELECT * FROM UserPlant WHERE userId LIKE (:userId)")
    fun getUserPlantsByUserId(userId: String): List<UserPlant>

    @Query("SELECT * FROM UserPlant WHERE id LIKE (:plantId)")
    fun getUserPlantsByPlantId (plantId: String): UserPlant

    @Delete
    fun deleteUserPlant(userPlant: UserPlant)
}