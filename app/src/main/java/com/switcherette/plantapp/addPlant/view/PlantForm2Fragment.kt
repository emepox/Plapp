package com.switcherette.plantapp.addPlant.view

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.switcherette.plantapp.R
import com.switcherette.plantapp.data.UserPlant
import com.switcherette.plantapp.databinding.FragmentPlantForm2Binding

class PlantForm2Fragment : Fragment(R.layout.fragment_plant_form2) {

    private lateinit var binding: FragmentPlantForm2Binding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentPlantForm2Binding.bind(view)

        val args: PlantForm2FragmentArgs by navArgs()
        var finalUserPlant = args.userPlant

        with(binding) {
            setLabelsForWater()

            setLabelsForLight()

            setRecommendedValuesLightAndWater(finalUserPlant)

            btnSave.setOnClickListener {

                val light = slLight.value
                finalUserPlant = finalUserPlant?.copy(light = light.toString())

                val water = slWater.value
                finalUserPlant = finalUserPlant?.copy(water = water.toString())

            }

        }

    }

    private fun FragmentPlantForm2Binding.setRecommendedValuesLightAndWater(
        finalUserPlant: UserPlant?
    ) {
        if (finalUserPlant?.water.isNullOrBlank()) {
            slWater.value = 3.0F
        } else {
            finalUserPlant?.water?.let { slWater.value = it.toFloat() }
        }

        if (finalUserPlant?.light.isNullOrBlank()) {
            slLight.value = 3.0F
        } else {
            finalUserPlant?.light?.let { slLight.value = it.toFloat() }
        }
    }

    private fun FragmentPlantForm2Binding.setLabelsForWater() {
        slWater.setLabelFormatter {
            when (it.toInt()) {
                1 -> "every 3 days"
                2 -> "every week"
                3 -> "every 2 weeks"
                4 -> "every 3 weeks"
                5 -> "monthly"
                else -> "unknown"
            }
        }
    }

    private fun FragmentPlantForm2Binding.setLabelsForLight() {
        slLight.setLabelFormatter {
            when (it.toInt()) {
                1 -> "shadow"
                2 -> "half shadow"
                3 -> "medium"
                4 -> "partly sun"
                5 -> "direct sun"
                else -> "unknown"
            }
        }
    }
}

/* btnSave.setOnClickListener {

     etLight.text.toString().takeIf { it.isNotBlank() }?.let {
         finalUserPlant = finalUserPlant?.copy(light = it)
     } ?: run {
         Toast.makeText(requireContext(), "please fill all fields", Toast.LENGTH_SHORT)
             .show()
         etLight.error = "Please fill this field"
     }

     etWater.text.toString().takeIf { it.isNotBlank() }?.let {
         finalUserPlant = finalUserPlant?.copy(water = it)
     } ?: run {
         Toast.makeText(requireContext(), "please fill all fields", Toast.LENGTH_SHORT)
             .show()
         etWater.error = "Please fill this field"
     }


     finalUserPlant?.userId = Firebase.auth.currentUser?.uid!!


}
*/
