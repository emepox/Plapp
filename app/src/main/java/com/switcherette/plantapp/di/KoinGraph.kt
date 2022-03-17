package com.switcherette.plantapp.di

import com.switcherette.plantapp.data.repositories.PlantIdRepo
import com.switcherette.plantapp.room.WaterDBConnection
import com.switcherette.plantapp.room.WaterRepo
import org.koin.dsl.module

object KoinGraph {

    val mainModule = module {
        single { PlantIdRepo() }
        single { WaterDBConnection(get()) }
        single { WaterRepo() }
    }
}