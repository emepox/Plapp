package com.switcherette.plantapp.addPlant.view

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.switcherette.plantapp.R
import com.switcherette.plantapp.databinding.FragmentPlantForm2Binding

class PlantForm2Fragment : Fragment(R.layout.fragment_plant_form2) {

    private lateinit var binding: FragmentPlantForm2Binding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentPlantForm2Binding.bind(view)

        val args: PlantForm2FragmentArgs by navArgs()
        var finalUserPlant = args.userPlant

        with(binding) {

            etWater.setText(finalUserPlant?.water.toString())
            etLight.setText(finalUserPlant?.light.toString())

            btnSave.setOnClickListener {

                etLight.text.toString().takeIf { it.isNotBlank() }?.let {
                    finalUserPlant = finalUserPlant?.copy(light = it.toInt())
                } ?: run {
                    Toast.makeText(requireContext(), "please fill all fields", Toast.LENGTH_SHORT)
                        .show()
                    etLight.error = "Please fill this field"
                }

                etWater.text.toString().takeIf { it.isNotBlank() }?.let {
                    finalUserPlant = finalUserPlant?.copy(water = it.toInt())
                } ?: run {
                    Toast.makeText(requireContext(), "please fill all fields", Toast.LENGTH_SHORT)
                        .show()
                    etWater.error = "Please fill this field"
                }


                finalUserPlant?.userId = Firebase.auth.currentUser?.uid!!

            }

        }

    }
}