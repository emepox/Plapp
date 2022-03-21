package com.switcherette.plantapp.addPlant.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.switcherette.plantapp.data.PlantInfo
import com.switcherette.plantapp.data.UserPlant
import com.switcherette.plantapp.data.repositories.PlantInfoRepository
import com.switcherette.plantapp.data.repositories.UserPlantRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class SearchByNameViewModel(
    private val plantInfoRepo: PlantInfoRepository
) : ViewModel(){

    var allPlants: MutableLiveData<List<PlantInfo>> = MutableLiveData()

    fun getAllPlants() {
        viewModelScope.launch(Dispatchers.IO) {
            val result = plantInfoRepo.getAllPlants()

            withContext(Dispatchers.Main){
                allPlants.value = result
            }
        }

    }

}