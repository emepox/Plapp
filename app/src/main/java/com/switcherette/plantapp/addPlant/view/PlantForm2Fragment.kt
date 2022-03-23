package com.switcherette.plantapp.addPlant.view

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.switcherette.plantapp.R
import com.switcherette.plantapp.addPlant.viewModel.PlantForm2ViewModel
import com.switcherette.plantapp.data.UserPlant
import com.switcherette.plantapp.databinding.FragmentPlantForm2Binding
import org.koin.androidx.viewmodel.ext.android.viewModel


class PlantForm2Fragment : Fragment(R.layout.fragment_plant_form2) {

    private val plantForm2ViewModel: PlantForm2ViewModel by viewModel()
    private lateinit var binding: FragmentPlantForm2Binding
    private var finalUserPlant: UserPlant? = null
    private val waterConverter =
        mapOf(Pair(3, 1), Pair(5, 2), Pair(7, 3), Pair(14, 4), Pair(15, 5), Pair(30, 6))


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentPlantForm2Binding.bind(view)

        val args: PlantForm2FragmentArgs by navArgs()
        finalUserPlant = args.userPlant

        with(binding) {
            setLabelsForWater()
            setLabelsForLight()
            setRecommendedValuesLightAndWater()
            setSaveListener()
        }
    }

    private fun FragmentPlantForm2Binding.setSaveListener() {
        finalUserPlant
        btnSave.setOnClickListener {

            val light = slLight.value
            finalUserPlant = finalUserPlant?.copy(light = light.toInt())

            val water = slWater.value
            finalUserPlant =
                finalUserPlant?.copy(water = waterConverter.entries.find { it.value == water.toInt() }!!.key)

            plantForm2ViewModel.writePlant(finalUserPlant!!)
            plantForm2ViewModel.addPlantToFirestore(finalUserPlant)

            findNavController().navigate(R.id.myPlantsFragment)

            Toast.makeText(
                requireContext(),
                "Plapp! ${finalUserPlant?.nickname ?: "Your green friend"} has been added to the family :)",
                Toast.LENGTH_SHORT
            ).show()

        }
    }


    private fun FragmentPlantForm2Binding.setRecommendedValuesLightAndWater() {
        if (finalUserPlant?.water == null) {
            slWater.value = 3.0F
        } else {
            finalUserPlant!!.water.let { slWater.value = waterConverter.getValue(it).toFloat() }
        }

        if (finalUserPlant?.light == null) {
            slLight.value = 3.0F
        } else {
            finalUserPlant!!.light.let { slLight.value = it.toFloat() }
        }
    }

    private fun FragmentPlantForm2Binding.setLabelsForWater() {
        slWater.setLabelFormatter {
            when (it.toInt()) {
                1 -> "every 30 days"
                2 -> "every 15 days"
                3 -> "every 14 days"
                4 -> "every 7 days"
                5 -> "every 5 days"
                6 -> "every 3 days"
                else -> "unknown"
            }
        }
    }

    private fun FragmentPlantForm2Binding.setLabelsForLight() {
        slLight.setLabelFormatter {
            when (it.toInt()) {
                1 -> "Full shade"
                2 -> "Partial shade"
                3 -> "Partial sun"
                4 -> "Full sun"
                else -> "unknown"
            }
        }
    }
}



