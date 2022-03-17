package com.switcherette.plantapp.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.switcherette.plantapp.data.WaterEvent


@Database(entities = [WaterEvent::class], version = 1)
abstract class WaterDB: RoomDatabase(){
    abstract fun waterDao(): WaterDao
}

class WaterDBConnection(context: Context){
    val instance: WaterDB = Room.databaseBuilder(context, WaterDB::class.java, "WaterDB").build()
}