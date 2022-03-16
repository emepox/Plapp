package com.switcherette.plantapp.di

import com.switcherette.plantapp.addPlant.viewmodel.SearchByPictureViewModel
import com.switcherette.plantapp.data.repositories.PlantIdRepository
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

object KoinGraph {

    val mainModule = module {
        single { PlantIdRepository() }
        viewModel { SearchByPictureViewModel(get()) }
    }
}