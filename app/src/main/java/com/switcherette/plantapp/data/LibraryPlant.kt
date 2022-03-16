package com.switcherette.plantapp.data

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity
data class LibraryPlant(
    @PrimaryKey val id: String,
    @ColumnInfo(name = "latinName") val latinName: String,
    @ColumnInfo(name = "commonName") val commonName: String,
    @ColumnInfo(name = "climat") val climat: String,
    @ColumnInfo(name = "tempMax") val tempMax: Int,
    @ColumnInfo(name = "tempMin") val tempMin: Int,
    @ColumnInfo(name = "light") val light: String,
    @ColumnInfo(name = "water") val water: String,
    @ColumnInfo(name = "disease") val disease: String
) : Parcelable

