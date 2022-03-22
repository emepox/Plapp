package com.switcherette.plantapp.di


import androidx.room.Room
import com.switcherette.plantapp.addPlant.viewModel.*
import com.switcherette.plantapp.calendar.viewModel.CalendarViewModel
import com.switcherette.plantapp.data.repositories.*
import com.switcherette.plantapp.data.room.AppDB
import com.switcherette.plantapp.home.HomePlantViewModel
import com.switcherette.plantapp.myPlants.viewModel.MyPlantsViewModel
import com.switcherette.plantapp.utils.WaterAlarm
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

object KoinGraph {

    val mainModule = module {

        single {
            Room.databaseBuilder(get(), AppDB::class.java, "AppDB")
                .createFromAsset("PlantInfo.db")
                .build()
        }
        single { get<AppDB>().waterDao() }
        single { get<AppDB>().plantDao() }
        single { get<AppDB>().plantInfoDao() }
        single { WaterAlarm(get()) }
        single { WaterRepository() }
        single { UserPlantRepository() }
        single { ApiPlantIdRepository() }
        single { RandomQuotesRepository() }
        single { PlantInfoRepository() }
        viewModel { SearchByPictureViewModel(get()) }
        viewModel { AddPlantPictureViewModel() }
        viewModel { HomePlantViewModel(get(), get()) }
        viewModel { PlantForm1ViewModel(get()) }
        viewModel { PlantFormViewModel(get(), get()) }
        viewModel { MyPlantsViewModel(get()) }
        viewModel { CalendarViewModel(get()) }
        viewModel { SearchByNameViewModel(get()) }
    }
}