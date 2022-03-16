package com.switcherette.plantapp.data

data class PlantId(
    val is_plant: Boolean,
    val suggestions: List<Suggestion>
)

data class Suggestion(
    val id: Int,
    val plant_details: PlantDetails,
    val plant_name: String,
    val probability: Double
)

data class PlantDetails(
    val common_names: List<String>,
    val scientific_name: String
)