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

        identifyPlant()
        observePlantId()
    }

    private fun identifyPlant() {
        searchPicVM.identifyPlant(arguments?.get("plant") as Uri)
    }


    private fun observePlantId() {
        searchPicVM.plantId.observe(viewLifecycleOwner) {
            if (it != null) {
                if (it.is_plant) {
                    val recyclerView = binding.rvSuggestions

                    recyclerView.layoutManager =
                        LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

                    val suggestionsAdapter = SuggestionsAdapter(it.suggestions) {
                        findNavController().navigate(R.id.action_searchByPictureFragment_to_plantFormFragment,
                            Bundle().apply {
                                putParcelable("suggestion", it)
                            }
                        )
                    }

                    recyclerView.adapter = suggestionsAdapter

                    //binding.ivLoading.visibility = View.GONE
                    binding.rvSuggestions.visibility = View.VISIBLE

                } else {
                    // binding.ivLoading.visibility = View.GONE
                    binding.tvSuggestions.text = "This does not seem to be a plant... try again?"
                }
            }
        }
    }
}