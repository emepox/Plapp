package com.switcherette.plantapp.addPlant.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.switcherette.plantapp.R
import com.switcherette.plantapp.data.UserPlant
import com.switcherette.plantapp.databinding.FragmentSearchByNameBinding


class SearchByNameFragment : Fragment(R.layout.fragment_search_by_name) {

    private lateinit var binding: FragmentSearchByNameBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentSearchByNameBinding.bind(view)

        binding.btnGo2Form.setOnClickListener {
            val mockUserPlant = UserPlant(
                scientificName = "Kaktus",
                commonName = "Spezieller Kaktus",
                family = "Kaktue realus",
                description = "It hurts a lot if you touch it",
                light = "lots of light",
                userId = Firebase.auth.currentUser?.uid.orEmpty()
            )
            val action = SearchByNameFragmentDirections.actionSearchByNameFragmentToPlantForm1Fragment(mockUserPlant)
            findNavController().navigate(action)
        }
    }
}

