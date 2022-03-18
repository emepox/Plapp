package com.switcherette.plantapp.room


import androidx.room.Database
import androidx.room.RoomDatabase

import com.switcherette.plantapp.data.PlantInfo
import com.switcherette.plantapp.data.UserPlant
import com.switcherette.plantapp.data.WaterEvent



@Database(entities = [WaterEvent::class, UserPlant::class, PlantInfo::class], version = 1)
abstract class AppDB: RoomDatabase(){
    abstract fun waterDao(): WaterDao
    abstract fun plantDao(): PlantDao
    abstract fun plantInfoDao(): PlantInfoDao
}
