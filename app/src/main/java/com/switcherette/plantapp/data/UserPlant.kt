package com.switcherette.plantapp.data

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity
data class UserPlant(
    @PrimaryKey val id: String,
    @ColumnInfo(name = "userId") val userId: String,
    @ColumnInfo(name = "nickname") val nickname: String,
    @ColumnInfo(name = "scientificName") val scientificName: String,
    @ColumnInfo(name = "commonName") val commonName: String,
    @ColumnInfo(name = "family") val family: String,
    @ColumnInfo(name = "description") val description: String,
    @ColumnInfo(name = "cultivation") val cultivation: String,
    @ColumnInfo(name = "light") val light: String,
    @ColumnInfo(name = "water") val water: String,
    @ColumnInfo(name = "disease") val disease: String
) : Parcelable

