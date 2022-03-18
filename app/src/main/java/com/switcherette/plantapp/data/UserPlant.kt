package com.switcherette.plantapp.data

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.parcelize.Parcelize
import java.util.*

@Parcelize
@Entity
data class UserPlant(
    @PrimaryKey var id: String = UUID.randomUUID().toString(),
    @ColumnInfo(name = "nickname") var nickname: String = "",
    @ColumnInfo(name = "scientificName") var scientificName: String? = null,
    @ColumnInfo(name = "commonName") var commonName: String? = null,
    @ColumnInfo(name = "family") var family: String? = null,
    @ColumnInfo(name = "description") var description: String? = null,
    @ColumnInfo(name = "cultivation") var cultivation: String? = null,
    @ColumnInfo(name = "light") var light: String = "",
    @ColumnInfo(name = "water") var water: String = "",
    @ColumnInfo(name = "userId") var userId: String
) : Parcelable
