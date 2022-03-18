package com.switcherette.plantapp.addPlant.view


import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.switcherette.plantapp.R
import com.switcherette.plantapp.addPlant.adapter.SuggestionsAdapter
import com.switcherette.plantapp.addPlant.viewModel.SearchByPictureViewModel
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
        observePlantId()
    }


    private fun observeConfirmationBtn() {
        searchPicVM.finalPath.observe(viewLifecycleOwner) {
            if (it != null) binding.btnStartPlantId.isEnabled = true
        }
    }

    private fun setClickListeners() {
        binding.btnChangeImage.setOnClickListener { showOptionsDialog() }

        binding.btnStartPlantId.setOnClickListener {
            with(binding){
                btnChangeImage.visibility = View.GONE
                btnStartPlantId.visibility = View.GONE
                ivLoading.visibility = View.VISIBLE
                tvSuggestions.visibility = View.VISIBLE
            }

            searchPicVM.identifyPlant()
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
//                searchPicVM.finalPath.value = requireContext().cacheDir.absolutePath + uri
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
                searchPicVM.finalPath.value =  uri
            }
        }


    private fun observePlantId() {
        searchPicVM.plantId.observe(viewLifecycleOwner) {
            if (it != null) {
                if (it.is_plant) {
                    val recyclerView = binding.rvSuggestions

                    recyclerView.layoutManager =
                        LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

                    val suggestionsAdapter = SuggestionsAdapter(it.suggestions) {
                        findNavController().navigate(R.id.action_searchByPictureFragment_to_plantForm1Fragment,
                            Bundle().apply {
                                putParcelable("suggestion", it)
                            }
                        )
                    }

                    recyclerView.adapter = suggestionsAdapter

                    binding.ivLoading.visibility = View.GONE
                    binding.rvSuggestions.visibility = View.VISIBLE

                } else {
                    binding.ivLoading.visibility = View.GONE
                    binding.tvSuggestions.text = "This does not seem to be a plant... try again?"
                }
            }
        }
    }
}