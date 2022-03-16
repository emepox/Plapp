package com.switcherette.plantapp.di

import com.switcherette.plantapp.data.repositories.PlantIdRepo
import org.koin.dsl.module

object KoinGraph {

    val mainModule = module {
        single { PlantIdRepo() }
    }
}