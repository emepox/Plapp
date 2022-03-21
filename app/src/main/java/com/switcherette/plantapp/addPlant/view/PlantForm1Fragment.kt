package com.switcherette.plantapp.addPlant.view

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.switcherette.plantapp.R
import com.switcherette.plantapp.databinding.FragmentPlantForm1Binding

class PlantForm1Fragment : Fragment(R.layout.fragment_plant_form1) {

    private lateinit var binding: FragmentPlantForm1Binding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentPlantForm1Binding.bind(view)
        val args: PlantForm1FragmentArgs by navArgs()
        val userPlant = args.userPlantFromAPI

        with(binding) {
            etNickname.setText(userPlant?.nickname)
            etScientificName.setText(userPlant?.scientificName)
            etCommonName.setText(userPlant?.commonName)

            btnNext.setOnClickListener {
                var finalUserPlant = args.userPlantFromAPI

                binding.etNickname.text.toString().takeIf { it.isNotBlank() }?.let {
                    finalUserPlant = finalUserPlant?.copy(nickname = it)
                } ?: run {
                    //show error
                    Toast.makeText(requireContext(), "Please give your plant a nickname", Toast.LENGTH_SHORT)
                        .show()
                    binding.etNickname.error = "Please give your plant a nickname"
                }

                binding.etCommonName.text.toString().let {
                    finalUserPlant = finalUserPlant?.copy(commonName = it)
                }

                binding.etScientificName.text.toString().let {
                    finalUserPlant = finalUserPlant?.copy(scientificName = it)
                }

                val action = PlantForm1FragmentDirections
                    .actionPlantForm1FragmentToPlantForm2Fragment(finalUserPlant)
                findNavController().navigate(action)
            }
        }
    }
}
