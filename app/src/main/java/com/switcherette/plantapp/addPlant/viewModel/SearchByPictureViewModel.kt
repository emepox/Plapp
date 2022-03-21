package com.switcherette.plantapp.addPlant.viewModel

import android.net.Uri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.switcherette.plantapp.data.PlantId
import com.switcherette.plantapp.data.repositories.ApiPlantIdRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.core.component.KoinComponent


class SearchByPictureViewModel(
    private val apiPlantIdRepo: ApiPlantIdRepository
) : ViewModel(), KoinComponent {

    var plantId: MutableLiveData<PlantId> = MutableLiveData()

    fun identifyPlant(path: Uri) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = apiPlantIdRepo.getPlantId(path)
            withContext(Dispatchers.Main){
                plantId.value = result!!
            }
        }

    }

}