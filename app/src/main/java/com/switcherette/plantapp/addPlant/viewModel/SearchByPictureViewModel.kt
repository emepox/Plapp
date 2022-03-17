package com.switcherette.plantapp.addPlant.viewModel

import android.content.Context
import android.net.Uri
import androidx.core.content.FileProvider
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.switcherette.plantapp.BuildConfig
import com.switcherette.plantapp.data.PlantId
import com.switcherette.plantapp.data.repositories.PlantIdRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import java.io.File


class SearchByPictureViewModel(
    private val plantIdRepo: PlantIdRepository
) : ViewModel(), KoinComponent {

    val context: Context by inject()

    var finalPath: MutableLiveData<Uri> = MutableLiveData()
    var plantId: MutableLiveData<PlantId> = MutableLiveData()

    fun getTmpFileUri(): Uri {
        val tmpFile =
            File.createTempFile("tmp_image_file", ".png", context.cacheDir).apply {
                createNewFile()
                deleteOnExit()
            }
        return FileProvider.getUriForFile(
            context,
            "${BuildConfig.APPLICATION_ID}.provider",
            tmpFile
        )
    }

    fun identifyPlant() {
        viewModelScope.launch(Dispatchers.IO) {
            val result = plantIdRepo.getPlantId(finalPath.value!!)
            withContext(Dispatchers.Main){
                plantId.value = result!!
            }
        }

    }

}