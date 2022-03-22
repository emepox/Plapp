package com.switcherette.plantapp.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class PlantId(
    val is_plant: Boolean,
    val suggestions: List<Suggestion>,
    val images: List<Images>
) : Parcelable

@Parcelize
data class Images(
    val url: String
) : Parcelable

@Parcelize
data class Suggestion(
    val id: Int,
    val plant_details: PlantDetails,
    val plant_name: String,
    val probability: Double
) : Parcelable

@Parcelize
data class PlantDetails(
    val common_names: List<String>?,
    val scientific_name: String,
    val wiki_image: WikiImage?
) : Parcelable

@Parcelize
data class WikiImage(
    val value: String,
    val citation: String
) : Parcelable

