package com.switcherette.plantapp.di

import com.switcherette.plantapp.addPlant.viewModel.PlantFormViewModel
import com.switcherette.plantapp.room.WaterDBConnection
import com.switcherette.plantapp.room.WaterRepository
import com.switcherette.plantapp.addPlant.viewModel.SearchByPictureViewModel
import com.switcherette.plantapp.data.repositories.PlantIdRepository
import com.switcherette.plantapp.utils.WaterAlarm
import org.koin.androidx.viewmodel.dsl.viewModel

import org.koin.dsl.module

object KoinGraph {

    val mainModule = module {
        single { WaterAlarm() }
        single { WaterDBConnection(get()) }
        single { WaterRepository() }
        single { PlantIdRepository() }
        viewModel { SearchByPictureViewModel(get()) }
        viewModel{PlantFormViewModel(get(), get())}
    }
}