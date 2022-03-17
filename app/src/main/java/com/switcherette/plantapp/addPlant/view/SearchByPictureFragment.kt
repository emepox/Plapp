package com.switcherette.plantapp.addPlant.view

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import androidx.lifecycle.lifecycleScope
import com.switcherette.plantapp.BuildConfig
import com.switcherette.plantapp.R
import com.switcherette.plantapp.databinding.FragmentSearchByNameBinding
import com.switcherette.plantapp.databinding.FragmentSearchByPictureBinding
import java.io.File

class SearchByPictureFragment : Fragment(R.layout.fragment_search_by_picture) {

    private lateinit var binding: FragmentSearchByPictureBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSearchByPictureBinding.bind(view)

        setClickListeners()
    }

    private fun setClickListeners() {
        binding.btnTakeImage.setOnClickListener { takeImage() }
        binding.btnSelectImage.setOnClickListener { selectImageFromGallery() }
    }

    private fun takeImage() {
        lifecycleScope.launchWhenStarted {
            getTmpFileUri().let { uri ->
                latestTmpUri = uri
                takeImageResult.launch(uri)
            }
        }
    }

    private val takeImageResult = registerForActivityResult(ActivityResultContracts.TakePicture()) { isSuccess ->
        if (isSuccess) {
            latestTmpUri?.let { uri ->
                previewImage.setImageURI(uri)
            }
        }
    }

    private fun selectImageFromGallery() = selectImageFromGalleryResult.launch("image/*")

    private val selectImageFromGalleryResult = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.let { previewImage.setImageURI(uri) }
    }

    private var latestTmpUri: Uri? = null
    private val previewImage by lazy { binding.ivPreview }

    private fun getTmpFileUri(): Uri {
        val tmpFile = File.createTempFile("tmp_image_file", ".png", requireContext().cacheDir).apply {
            createNewFile()
            deleteOnExit()
        }

        return FileProvider.getUriForFile(requireContext(), "${BuildConfig.APPLICATION_ID}.provider", tmpFile)
    }
}