package com.switcherette.plantapp.data

data class PlantsRequest(
    val images: List<String>,
    val plant_details: List<String> = listOf("common_names", "wiki_image")
)
