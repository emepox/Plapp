package com.switcherette.plantapp.addPlant.viewmodel

import android.content.Context
import android.net.Uri
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.FileProvider
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.switcherette.plantapp.BuildConfig
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import java.io.File


class SearchByPictureViewModel : ViewModel(), KoinComponent {

    val context: Context by inject()

    var finalUri: MutableLiveData<Uri> = MutableLiveData()

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