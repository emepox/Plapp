package com.switcherette.plantapp.di


import com.switcherette.plantapp.addPlant.viewModel.AddPlantPictureViewModel
import androidx.room.Room
import com.switcherette.plantapp.addPlant.viewModel.PlantForm1ViewModel
import com.switcherette.plantapp.addPlant.viewModel.PlantFormViewModel
import com.switcherette.plantapp.addPlant.viewModel.SearchByPictureViewModel
import com.switcherette.plantapp.data.repositories.PlantIdRepository
import com.switcherette.plantapp.data.repositories.PlantLibraryRepository
import com.switcherette.plantapp.data.repositories.RandomQuotesRepository
import com.switcherette.plantapp.home.HomePlantViewModel
import com.switcherette.plantapp.data.room.AppDB
import com.switcherette.plantapp.data.room.WaterRepository
import com.switcherette.plantapp.utils.WaterAlarm
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

object KoinGraph {

    val mainModule = module {

      single {
            Room.databaseBuilder(get(), AppDB::class.java, "AppDB")
                .createFromAsset("plants_db.db")
                .build()
        }

        single { get<AppDB>().waterDao() }
        single { get<AppDB>().plantDao() }
        single { get<AppDB>().plantInfoDao() }
        factory { WaterAlarm(get()) }
        single { WaterRepository() }
        single { PlantIdRepository() }
        single { RandomQuotesRepository() }
        single { PlantLibraryRepository() }
        viewModel { SearchByPictureViewModel(get()) }
        viewModel { AddPlantPictureViewModel() }
        viewModel { HomePlantViewModel(get(), get()) }
        viewModel { PlantFormViewModel(get()) }
        viewModel { PlantForm1ViewModel(get()) }
    }
}