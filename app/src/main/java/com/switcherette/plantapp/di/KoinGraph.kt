package com.switcherette.plantapp.di

import com.switcherette.plantapp.room.WaterDBConnection
import com.switcherette.plantapp.room.WaterRepo
import com.switcherette.plantapp.addPlant.viewModel.SearchByPictureViewModel
import com.switcherette.plantapp.data.repositories.PlantIdRepository
import org.koin.androidx.viewmodel.dsl.viewModel

import org.koin.dsl.module

object KoinGraph {

    val mainModule = module {
        single { WaterDBConnection(get()) }
        single { WaterRepo() }
        single { PlantIdRepository() }
        viewModel { SearchByPictureViewModel(get()) }
    }
}