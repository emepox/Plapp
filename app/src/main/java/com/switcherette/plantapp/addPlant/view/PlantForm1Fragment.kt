package com.switcherette.plantapp.addPlant.view

import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.switcherette.plantapp.R
import com.switcherette.plantapp.addPlant.viewModel.PlantForm1ViewModel
import com.switcherette.plantapp.data.Suggestion
import com.switcherette.plantapp.data.UserPlant
import com.switcherette.plantapp.databinding.FragmentPlantForm1Binding
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*

class PlantForm1Fragment : Fragment(R.layout.fragment_plant_form1) {

    private lateinit var binding: FragmentPlantForm1Binding
    private val plantForm1VM: PlantForm1ViewModel by viewModel()
    private lateinit var finalUserPlant: UserPlant

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentPlantForm1Binding.bind(view)

        // get API info from Bundle
        val args: PlantForm1FragmentArgs by navArgs()
        val apiSuggestion = args.suggestionFromApi
        val imageFromUser: Uri = args.photoFromUser

        // initialise UserPlant object
        finalUserPlant = UserPlant(
            UUID.randomUUID().toString(),
            "",
            null,
            null,
            null,
            null,
            null,
            4,
            15,
            imageFromUser.path,
            Firebase.auth.currentUser?.uid.orEmpty()
        )

        apiSuggestion?.plant_details?.scientific_name?.let { getInfoFromPlantLibrary(it) }
        observeFinalUserPlant(finalUserPlant, apiSuggestion)
        handleOnClick(finalUserPlant)
    }

    private fun observeFinalUserPlant(
        finalUserPlant: UserPlant,
        apiSuggestion: Suggestion?
    ) {
        plantForm1VM.plantInfoAPI.observe(viewLifecycleOwner) {
            if (it?.scientificName == null) {
                finalUserPlant.scientificName = apiSuggestion?.plant_details?.scientific_name
            } else finalUserPlant.scientificName = it.scientificName

            if (it?.commonName == null) {
                finalUserPlant.commonName =
                    apiSuggestion?.plant_details?.common_names.toString().drop(1).dropLast(1)
            } else finalUserPlant.commonName = it.commonName

            if (it?.commonName == null) {
                finalUserPlant.image =
                    apiSuggestion?.plant_details?.wiki_image?.value
            } else finalUserPlant.image = it.img

            finalUserPlant.family = it?.family
            finalUserPlant.description = it?.description
            finalUserPlant.cultivation = it?.cultivation
            finalUserPlant.light = it?.light ?: 4
            finalUserPlant.water = it?.water ?: 15

            binding.etScientificName.setText(finalUserPlant.scientificName)
            binding.etCommonName.setText(finalUserPlant.commonName)

        }
    }

    private fun handleOnClick(userPlant: UserPlant) {
        var finalUserPlant = userPlant


        binding.btnNext.setOnClickListener {
            with(binding) {
                // Check inputs
                etNickname.text.toString().takeIf { it.isNotBlank() }?.let {
                    finalUserPlant = finalUserPlant.copy(nickname = it)
                } ?: run {
                    //show error
                    Toast.makeText(
                        requireContext(),
                        "Please give your plant a nickname",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                    etNickname.error = "Please give your plant a nickname"
                }

                etCommonName.text.toString().let {
                    finalUserPlant = finalUserPlant.copy(commonName = it)
                }

                etScientificName.text.toString().let {
                    finalUserPlant = finalUserPlant.copy(scientificName = it)
                }

                val action = PlantForm1FragmentDirections
                    .actionPlantForm1FragmentToPlantForm2Fragment(finalUserPlant)
                findNavController().navigate(action)
            }

        }
    }

    private fun getInfoFromPlantLibrary(name: String) {
        plantForm1VM.getPlantFromLibrary(name)
    }
}
