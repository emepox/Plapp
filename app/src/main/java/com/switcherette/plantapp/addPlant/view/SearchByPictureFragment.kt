package com.switcherette.plantapp.addPlant.view


import android.content.res.Resources
import android.graphics.Canvas
import android.graphics.Paint
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.annotation.ColorInt
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.switcherette.plantapp.R
import com.switcherette.plantapp.addPlant.adapter.SuggestionsAdapter
import com.switcherette.plantapp.addPlant.viewModel.SearchByPictureViewModel
import com.switcherette.plantapp.databinding.FragmentSearchByPictureBinding
import org.koin.androidx.viewmodel.ext.android.viewModel


class SearchByPictureFragment : Fragment(R.layout.fragment_search_by_picture) {

    private val searchPicVM: SearchByPictureViewModel by viewModel()
    private lateinit var binding: FragmentSearchByPictureBinding
    private lateinit var uri: Uri

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSearchByPictureBinding.bind(view)

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
                        putParcelable("uri", uri)
                    }
                )
            }
        }
    }

    private fun identifyPlant() {
        binding.ivPreview.setImageURI(uri)
        searchPicVM.identifyPlant(uri)
    }


    private fun observePlantId() {
        searchPicVM.plantId.observe(viewLifecycleOwner) {
            if (it != null) {
                if (it.is_plant) {
                    val recyclerView = binding.rvSuggestions

                    recyclerView.layoutManager =
                        LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

                    val suggestionsAdapter = SuggestionsAdapter(it.suggestions) {
                        findNavController().navigate(R.id.action_searchByPictureFragment_to_plantFormFragment,
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
                    binding.tvSuggestions.text = getString(R.string.not_a_plant)
                }
            }
        }
    }
}





