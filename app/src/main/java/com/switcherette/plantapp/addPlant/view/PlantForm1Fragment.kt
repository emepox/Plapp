package com.switcherette.plantapp.addPlant.view

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.switcherette.plantapp.R
import com.switcherette.plantapp.data.UserPlantBuilder
import com.switcherette.plantapp.databinding.FragmentPlantForm1Binding

class PlantForm1Fragment: Fragment(R.layout.fragment_plant_form1) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

/*        val args = navArgs<>()

        val binding = FragmentPlantForm1Binding.bind(view)
        val plantBuilder = UserPlantBuilder(args.userPlant)

        binding.btnNext.setOnClickListener {
            // Check inputs
            binding.etNickname.text.toString().takeIf { it.isNotBlank() }?.let {
                plantBuilder.nickname = it
            } ?: run {
                //show error
                Toast.makeText(, "", Toast.LENGTH_SHORT).show()
                binding.etNickname.error = "Need to be not empty"
            }

            // All Good?
            findNavController().navigate(...) //give builder object as parameter
        }*/
    }
}