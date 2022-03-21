package com.switcherette.plantapp.addPlant.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.switcherette.plantapp.data.PlantInfo
import com.switcherette.plantapp.data.repositories.PlantLibraryRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class PlantForm1ViewModel(
    private val libraryRepository: PlantLibraryRepository,
) : ViewModel(), KoinComponent {

    var plantInfoAPI : MutableLiveData<PlantInfo?> = MutableLiveData()

    fun getPlantFromLibrary(scientificName: String){
        viewModelScope.launch(Dispatchers.IO) {
            val result = libraryRepository.getPlantByName(scientificName)
            withContext(Dispatchers.Main){
                plantInfoAPI.value = result
            }
        }

    }



}