package com.switcherette.plantapp.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class WaterEvent(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "plantId") val plantId: String,
    @ColumnInfo(name = "repeatStart") val repeatStart: Long,
    @ColumnInfo(name = "repeatInterval") val repeatInterval: Long,
)
