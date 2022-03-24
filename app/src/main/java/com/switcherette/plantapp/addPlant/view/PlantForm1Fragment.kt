package com.switcherette.plantapp.addPlant.view

import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.addCallback
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.res.ResourcesCompat
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
        requireActivity().onBackPressedDispatcher.addCallback(this) {}
        requireActivity().findViewById<ConstraintLayout>(R.id.cl_mainActivity).setBackgroundColor(
            ResourcesCompat.getColor(resources, R.color.primary, null))

        val args: PlantForm1FragmentArgs by navArgs()
        // get API info (if coming from SearchByPicture)
        val apiSuggestion = args.suggestionFromApi
        val imageFromUser: String? = args.photoFromUser
        // get Plant info (if coming from SearchByName)
        val plantFromSearchByName = args.userPlant

        // initialise UserPlant object
        finalUserPlant = UserPlant(
            UUID.randomUUID().toString(),
            "",
            null,
            null,
            null,
            null,
            null,
            2,
            15,
            imageFromUser,
            Firebase.auth.currentUser?.uid.orEmpty()
        )

        fillForm(apiSuggestion, plantFromSearchByName)
    }

    private fun fillForm(
        apiSuggestion: Suggestion?,
        plantFromSearchByName: UserPlant?
    ) {
        if (apiSuggestion != null) {
            getInfoFromPlantLibrary(apiSuggestion.plant_details.scientific_name)
            plantForm1VM.infoFromPlantLibrary.observe(viewLifecycleOwner) {
                if (it?.scientificName == null) {
                    finalUserPlant.scientificName = apiSuggestion.plant_details.scientific_name
                } else finalUserPlant.scientificName = it.scientificName

                if (it?.commonName == null) {
                    finalUserPlant.commonName =
                        apiSuggestion.plant_details.common_names.toString().drop(1).dropLast(1)
                } else finalUserPlant.commonName = it.commonName

                if (finalUserPlant.image == null) {
                    if (it?.img == null) {
                        finalUserPlant.image = apiSuggestion.plant_details.wiki_image?.value
                    } else finalUserPlant.image = it.img
                }

                finalUserPlant.family = it?.family
                finalUserPlant.description = it?.description
                finalUserPlant.cultivation = it?.cultivation
                finalUserPlant.light = it?.light ?: 2
                finalUserPlant.water = it?.water ?: 15

                binding.etScientificName.setText(finalUserPlant.scientificName)
                binding.etCommonName.setText(finalUserPlant.commonName)
                handleOnClick(finalUserPlant)
            }
        } else if (plantFromSearchByName != null) {
            finalUserPlant = plantFromSearchByName
            binding.etScientificName.setText(finalUserPlant.scientificName)
            binding.etCommonName.setText(finalUserPlant.commonName)

            handleOnClick(finalUserPlant)

        } else {
            finalUserPlant.image = "android.resource://com.switcherette.plantapp/" + R.drawable.plant_img.toString()
            handleOnClick(finalUserPlant)
        }


    }


    private fun handleOnClick(userPlant: UserPlant) {
        var finalUserPlant = userPlant

        binding.btnNext.setOnClickListener {
            with(binding) {
                // Check inputs
                etNickname.text.toString().takeIf { it.isNotBlank() }?.let {
                    finalUserPlant = finalUserPlant.copy(nickname = it)
                }

                etCommonName.text.toString().let {
                    finalUserPlant = finalUserPlant.copy(commonName = it)
                }

                etScientificName.text.toString().let {
                    finalUserPlant = finalUserPlant.copy(scientificName = it)
                }

                if(etNickname.text!!.isNotEmpty()) {
                    val action = PlantForm1FragmentDirections
                        .actionPlantForm1FragmentToPlantForm2Fragment(finalUserPlant)
                    findNavController().navigate(action)
                } else {
                    //show error
                    Toast.makeText(
                        requireContext(),
                        "Please give your plant a nickname",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                    etNickname.error = "Please give your plant a nickname"
                }

            }

        }
    }

    private fun getInfoFromPlantLibrary(name: String) {
        plantForm1VM.getPlantFromLibrary(name)
    }
}
