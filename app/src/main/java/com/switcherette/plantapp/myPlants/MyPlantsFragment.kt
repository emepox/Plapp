package com.switcherette.plantapp.myPlants

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.navigation.fragment.findNavController
import com.switcherette.plantapp.R
import com.switcherette.plantapp.databinding.FragmentMyPlantsBinding

class MyPlantsFragment : Fragment(R.layout.fragment_my_plants) {

    private lateinit var binding: FragmentMyPlantsBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentMyPlantsBinding.bind(view)

        binding.ibtnGo2AddPlant.setOnClickListener {
            findNavController().navigate(R.id.action_myPlantsFragment_to_howToIdentifyFragment)
        }
    }
}