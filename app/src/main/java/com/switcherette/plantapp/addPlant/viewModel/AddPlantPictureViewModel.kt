package com.switcherette.plantapp.addPlant.viewModel

import android.content.Context
import android.net.Uri
import androidx.core.content.FileProvider
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.switcherette.plantapp.BuildConfig
import com.switcherette.plantapp.data.repositories.PlantIdRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import java.io.File


class AddPlantPictureViewModel() : ViewModel(), KoinComponent {

    val context: Context by inject()

    var finalPath: MutableLiveData<Uri> = MutableLiveData()

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

}