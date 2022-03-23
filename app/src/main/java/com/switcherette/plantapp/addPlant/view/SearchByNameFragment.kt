package com.switcherette.plantapp.addPlant.view

import android.os.Bundle
import android.view.View
import androidx.activity.addCallback
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.switcherette.plantapp.R
import com.switcherette.plantapp.addPlant.adapter.SearchByNameAdapter
import com.switcherette.plantapp.addPlant.viewModel.SearchByNameViewModel
import com.switcherette.plantapp.data.PlantInfo
import com.switcherette.plantapp.data.UserPlant
import com.switcherette.plantapp.databinding.FragmentSearchByNameBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*


class SearchByNameFragment : Fragment(R.layout.fragment_search_by_name) {

    private lateinit var binding: FragmentSearchByNameBinding
    private val searchByNameVM: SearchByNameViewModel by viewModel()
    private var userPhotoUrl: String? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSearchByNameBinding.bind(view)
        requireActivity().onBackPressedDispatcher.addCallback(this) {}

        userPhotoUrl = arguments?.getString("userPhotoUrl")

        searchByNameVM.getAllPlants()
        setRecyclerView()
        binding.tvNoList.setOnClickListener { createEmptyUserPlant() }

    }

    private fun setRecyclerView() {
        val recyclerView = binding.rvSearchPlantByName

        recyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        val allPlantsAdapter = SearchByNameAdapter { choosePlant(it) }
        recyclerView.adapter = allPlantsAdapter

        searchByNameVM.allPlants.observe(viewLifecycleOwner) {
            allPlantsAdapter.submitList(it)
        }

        binding.etSearch.addTextChangedListener { text ->
            allPlantsAdapter.submitList(searchByNameVM.allPlants.value?.filter {
                (it.scientificName!!.contains(
                    text.toString(),
                    true
                )) || (it.commonName != null && it.commonName.contains(
                    text.toString(),
                    true
                ))
            })
        }
    }

    private fun choosePlant(plantInfo: PlantInfo) {

        val image = if (userPhotoUrl != null){
            userPhotoUrl
        } else {
            plantInfo.img
        }

        val userPlant = UserPlant(
            UUID.randomUUID().toString(),
            "",
            plantInfo.scientificName,
            plantInfo.commonName,
            plantInfo.family,
            plantInfo.description,
            plantInfo.cultivation,
            plantInfo.light ?: 2,
            plantInfo.water ?: 15,
            image,
            Firebase.auth.currentUser?.uid.orEmpty()
        )

        val action =
            SearchByNameFragmentDirections.actionSearchByNameFragmentToPlantForm1Fragment(userPlant)
        findNavController().navigate(action)
    }

    private fun createEmptyUserPlant() {
        findNavController().navigate(R.id.action_searchByNameFragment_to_plantForm1Fragment)
    }

}

