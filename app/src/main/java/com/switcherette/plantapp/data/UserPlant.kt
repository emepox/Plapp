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
    @ColumnInfo(name = "nickname") val nickname: String,
    @ColumnInfo(name = "scientificName") val scientificName: String?,
    @ColumnInfo(name = "commonName") val commonName: String?,
    @ColumnInfo(name = "family") val family: String?,
    @ColumnInfo(name = "description") val description: String?,
    @ColumnInfo(name = "cultivation") val cultivation: String?,
    @ColumnInfo(name = "light") val light: String,
    @ColumnInfo(name = "water") val water: String,
    @ColumnInfo(name = "disease") val disease: String?
) : Parcelable

@Parcelize
data class UserPlantBuilder(
    val userPlant: UserPlant,
    var nickname: String? = null,
    var scientificName: String? = null,
    var commonName: String? = null,
    var family: String? = null,
    var description: String? = null,
    var cultivation: String? = null,
    var light: String? = null,
    var water: String? = null
) : Parcelable {

    fun build() = UserPlant(
            id = userPlant.id,
            nickname = requireNotNull(nickname),
            scientificName = scientificName,
            commonName = commonName,
            family = family,
            description = description,
            cultivation = cultivation,
            light = light!!,
            water = water!!,
            disease = null
        )
}