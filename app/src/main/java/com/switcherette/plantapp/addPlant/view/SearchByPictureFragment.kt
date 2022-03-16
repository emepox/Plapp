package com.switcherette.plantapp.addPlant.view


import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.switcherette.plantapp.R
import com.switcherette.plantapp.addPlant.viewmodel.SearchByPictureViewModel
import com.switcherette.plantapp.databinding.FragmentSearchByPictureBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class SearchByPictureFragment : Fragment(R.layout.fragment_search_by_picture) {

    private val searchPicVM: SearchByPictureViewModel by viewModel()
    private lateinit var binding: FragmentSearchByPictureBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSearchByPictureBinding.bind(view)

        observeConfirmationBtn()
        showOptionsDialog()
        setClickListeners()
    }

    private fun observeConfirmationBtn() {
        searchPicVM.finalUri.observe(viewLifecycleOwner) {
            if (it != null) binding.btnConfirmImage.isEnabled = true
        }
    }

    private fun setClickListeners() {
        binding.btnChangeImage.setOnClickListener { showOptionsDialog() }
        binding.btnConfirmImage.setOnClickListener {
            findNavController().navigate(R.id.action_searchByPictureFragment_to_plantFormFragment,
                Bundle().apply {
                    putString("imageURI", searchPicVM.finalUri.value?.toString())
                })
        }
    }

    private fun showOptionsDialog() {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Let's take a look")
            .setMessage("We need an image to identify your plant")
            .setNeutralButton("Cancel") { dialog, which ->
                dialog.dismiss()
            }
            .setNegativeButton("Take photo") { dialog, which ->
                takeImage()
            }
            .setPositiveButton("Choose from gallery") { dialog, which ->
                selectImageFromGallery()
            }
            .show()
    }

    private var latestTmpUri: Uri? = null
    private val previewImage by lazy { binding.ivPreview }

    private fun takeImage() {
        lifecycleScope.launchWhenStarted {
            searchPicVM.getTmpFileUri().let { uri ->
                latestTmpUri = uri
                searchPicVM.finalUri.value = uri
                takeImageResult.launch(uri)
            }
        }
    }

    private val takeImageResult =
        registerForActivityResult(ActivityResultContracts.TakePicture()) { isSuccess ->
            if (isSuccess) {
                latestTmpUri?.let { uri ->
                    previewImage.setImageURI(uri)
                }
            }
        }

    private fun selectImageFromGallery() = selectImageFromGalleryResult.launch("image/*")

    private val selectImageFromGalleryResult =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            uri?.let {
                previewImage.setImageURI(uri)
                searchPicVM.finalUri.value = uri
            }
        }


}