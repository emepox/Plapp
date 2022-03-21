package com.switcherette.plantapp.addPlant.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.switcherette.plantapp.data.PlantInfo
import com.switcherette.plantapp.data.repositories.LibraryPlantRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.core.component.KoinComponent

class PlantForm1ViewModel(
    private val libraryPlantRepository: LibraryPlantRepository,
) : ViewModel(), KoinComponent {

    var plantInfoAPI : MutableLiveData<PlantInfo?> = MutableLiveData()

    fun getPlantFromLibrary(scientificName: String){
        viewModelScope.launch(Dispatchers.IO) {
            val result = libraryPlantRepository.getPlantByName(scientificName)
            withContext(Dispatchers.Main){
                plantInfoAPI.value = result
            }
        }

    }



}