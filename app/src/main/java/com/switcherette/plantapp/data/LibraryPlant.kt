package com.switcherette.plantapp.data

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
data class LibraryPlant(
    val id: String,
    val scientificName: String,
    val commonName: String,
    val family: String,
    val description: String,
    val cultivation: String,
    val light: String,
    val water: String,
    val disease: String
) : Parcelable

