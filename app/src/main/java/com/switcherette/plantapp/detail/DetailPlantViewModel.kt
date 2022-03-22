package com.switcherette.plantapp.detail

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.switcherette.plantapp.data.UserPlant
import com.switcherette.plantapp.data.repositories.UserPlantRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class DetailPlantViewModel(
    private val userPlantRepo: UserPlantRepository
): ViewModel(), KoinComponent {

    val context: Context by inject()

    fun deletePlant(plant: UserPlant) {
        viewModelScope.launch(Dispatchers.IO) {
            userPlantRepo.deleteUserPlant(plant)

            withContext(Dispatchers.Main){
                Toast.makeText(context, "Plant deleted", Toast.LENGTH_SHORT).show()
            }
        }
    }

}