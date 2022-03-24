package com.switcherette.plantapp.addPlant.view


import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.activity.addCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.switcherette.plantapp.R
import com.switcherette.plantapp.addPlant.viewModel.AddPlantPictureViewModel
import com.switcherette.plantapp.databinding.FragmentAddPlantPictureBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class AddPlantPictureFragment : Fragment(R.layout.fragment_add_plant_picture) {

    private val searchPicVM: AddPlantPictureViewModel by viewModel()
    private lateinit var binding: FragmentAddPlantPictureBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentAddPlantPictureBinding.bind(view)
        requireActivity().onBackPressedDispatcher.addCallback(this) {}
        requireActivity().findViewById<ConstraintLayout>(R.id.cl_mainActivity).setBackgroundColor(
            ResourcesCompat.getColor(resources, R.color.primary, null)
        )

        observeConfirmationBtn()
        setClickListeners()
    }

    private fun observeConfirmationBtn() {
        searchPicVM.finalPath.observe(viewLifecycleOwner) {
            if (it != null) binding.btnStartPlantId.isEnabled = true
        }
    }

    private fun setClickListeners() {

        with(binding) {
            cardPreview.setOnClickListener { showOptionsDialog() }
            fabEditPic.setOnClickListener { showOptionsDialog() }
            tvNoPic.setOnClickListener {
                findNavController().navigate(
                    R.id.action_addPlantPictureFragment_to_searchByNameFragment,
                )
            }

            btnStartPlantId.setOnClickListener {
                findNavController().navigate(
                    R.id.action_addPlantPictureFragment_to_searchByPictureFragment,
                    Bundle().apply {
                        putParcelable("picturePath", searchPicVM.finalPath.value)
                    }
                )
            }
        }
    }

    private fun showOptionsDialog() {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("You choose!")
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
                takeImageResult.launch(uri)
            }
        }
    }

    private val takeImageResult =
        registerForActivityResult(ActivityResultContracts.TakePicture()) { isSuccess ->
            if (isSuccess) {
                latestTmpUri?.let { uri ->
                    previewImage.setImageURI(uri)
                    searchPicVM.finalPath.value = uri
                }
            }
        }

    private fun selectImageFromGallery() = selectImageFromGalleryResult.launch("image/*")

    private val selectImageFromGalleryResult =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            uri?.let {
                previewImage.setImageURI(uri)
                searchPicVM.finalPath.value = uri
            }
        }
}