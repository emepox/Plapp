package com.switcherette.plantapp.addPlant.view


import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.activity.addCallback
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.switcherette.plantapp.R
import com.switcherette.plantapp.addPlant.adapter.SearchByPictureAdapter
import com.switcherette.plantapp.addPlant.viewModel.SearchByPictureViewModel
import com.switcherette.plantapp.databinding.FragmentSearchByPictureBinding
import org.koin.androidx.viewmodel.ext.android.viewModel


class SearchByPictureFragment : Fragment(R.layout.fragment_search_by_picture) {

    private val searchPicVM: SearchByPictureViewModel by viewModel()
    private lateinit var binding: FragmentSearchByPictureBinding
    private lateinit var uri: Uri
    private lateinit var photoUrlFromAPI: String

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSearchByPictureBinding.bind(view)
        requireActivity().onBackPressedDispatcher.addCallback(this) {}
        requireActivity().findViewById<ConstraintLayout>(R.id.cl_mainActivity).setBackgroundColor(
            ResourcesCompat.getColor(resources, R.color.primary, null))

        uri = arguments?.get("picturePath") as Uri

        identifyPlant()
        observePlantId()
        setOnClickListeners()
    }

    private fun setOnClickListeners() {
        with(binding)
        {
            tvNoPic.setOnClickListener {
                findNavController().navigate(R.id.action_searchByPictureFragment_to_searchByNameFragment,
                    Bundle().apply {
                        putString("userPhotoUrl", photoUrlFromAPI)
                    }
                )
            }
            btnTryAgain.setOnClickListener {
                findNavController().navigate(R.id.action_searchByPictureFragment_to_addPlantPictureFragment)
            }
        }
    }

    private fun identifyPlant() {
        binding.ivPreview.setImageURI(uri)
        searchPicVM.identifyPlant(uri)
    }

    private fun observePlantId() {
        searchPicVM.plantId.observe(viewLifecycleOwner) { it ->
            if (it != null) {
                photoUrlFromAPI = it.images[0].url
                if (it.is_plant) {
                    val recyclerView = binding.rvSuggestions

                    recyclerView.layoutManager =
                        LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

                    val suggestionsAdapter = SearchByPictureAdapter(it.suggestions) { suggestion ->
                        val action = SearchByPictureFragmentDirections
                            .actionSearchByPictureFragmentToPlantForm1Fragment(suggestion, null, uri.toString(), null)
                        findNavController().navigate(action)
                    }

                    recyclerView.adapter = suggestionsAdapter
                    binding.pageIndicator.attachTo(recyclerView)

                    binding.ivLoading.visibility = View.GONE
                    binding.rvSuggestions.visibility = View.VISIBLE
                    binding.pageIndicator.visibility = View.VISIBLE

                } else {
                    binding.ivLoading.visibility = View.GONE
                    binding.tvSuggestions.text = getString(R.string.not_a_plant)
                    binding.tvSuggestions.textSize = 20F
                }
            }
        }
    }
}





