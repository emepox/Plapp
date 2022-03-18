package com.switcherette.plantapp.di

import androidx.room.Room
import com.switcherette.plantapp.addPlant.viewModel.PlantFormViewModel
import com.switcherette.plantapp.room.WaterRepository
import com.switcherette.plantapp.addPlant.viewModel.SearchByPictureViewModel
import com.switcherette.plantapp.data.repositories.PlantIdRepository
import com.switcherette.plantapp.room.AppDB
import com.switcherette.plantapp.utils.WaterAlarm
import org.koin.androidx.viewmodel.dsl.viewModel

import org.koin.dsl.module

object KoinGraph {

    val mainModule = module {
        single { Room.databaseBuilder(get(), AppDB::class.java, "WaterDB").build() }
        single { get<AppDB>().waterDao() }
        factory { WaterAlarm(get()) }
        single { WaterRepository() }
        single { PlantIdRepository() }
        viewModel { SearchByPictureViewModel(get()) }
        viewModel{PlantFormViewModel(get())}
    }
}