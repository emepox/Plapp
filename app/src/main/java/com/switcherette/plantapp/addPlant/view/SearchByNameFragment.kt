package com.switcherette.plantapp.addPlant.view

import android.os.Bundle
import android.view.View
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentSearchByNameBinding.bind(view)

        searchByNameVM.getAllPlants()
        setRecyclerView()
        binding.tvNoList.setOnClickListener { createEmptyUserPlant() }
    }

    private fun setRecyclerView() {
        val recyclerView = binding.rvSearchPlantByName

        recyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        searchByNameVM.allPlants.observe(viewLifecycleOwner) { plant ->
            val allPlantsAdapter = SearchByNameAdapter(plant) { choosePlant(it) }
            recyclerView.adapter = allPlantsAdapter
        }
    }

    private fun choosePlant(plantInfo: PlantInfo) {
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
            plantInfo.img,
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

