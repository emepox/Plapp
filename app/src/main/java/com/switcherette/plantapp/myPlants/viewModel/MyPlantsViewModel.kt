package com.switcherette.plantapp.myPlants.viewModel

import android.net.Uri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.switcherette.plantapp.data.PlantId
import com.switcherette.plantapp.data.UserPlant
import com.switcherette.plantapp.data.repositories.PlantIdRepository
import com.switcherette.plantapp.data.room.PlantRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class MyPlantsViewModel(
    private val userPlantRepo: PlantRepository
) : ViewModel(){

    var userPlants: MutableLiveData<List<UserPlant>> = MutableLiveData()

    fun getUserPlants() {
        viewModelScope.launch(Dispatchers.IO) {
            val result = Firebase.auth.currentUser?.uid?.let {
                userPlantRepo.getUserPlantsByUserId(it)
            }
            withContext(Dispatchers.Main){
                userPlants.value = result!!
            }
        }
    }
}