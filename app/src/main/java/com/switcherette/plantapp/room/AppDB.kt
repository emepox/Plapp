package com.switcherette.plantapp.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.switcherette.plantapp.data.WaterEvent


@Database(entities = [WaterEvent::class, PlantDao::class], version = 1)
abstract class AppDB: RoomDatabase(){
    abstract fun waterDao(): WaterDao
    abstract fun plantDao(): PlantDao
}
