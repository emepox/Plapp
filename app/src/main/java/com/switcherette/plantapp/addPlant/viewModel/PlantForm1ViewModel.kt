package com.switcherette.plantapp.addPlant.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.switcherette.plantapp.data.PlantInfo
import com.switcherette.plantapp.data.repositories.PlantInfoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.core.component.KoinComponent

class PlantForm1ViewModel(
    private val plantInfoRepository: PlantInfoRepository,
) : ViewModel(), KoinComponent {

    var infoFromPlantLibrary : MutableLiveData<PlantInfo?> = MutableLiveData()

    fun getPlantFromLibrary(scientificName: String){
        viewModelScope.launch(Dispatchers.IO) {
            val result = plantInfoRepository.getPlantByName(scientificName)
            withContext(Dispatchers.Main){
                infoFromPlantLibrary.value = result
            }
        }
    }
}