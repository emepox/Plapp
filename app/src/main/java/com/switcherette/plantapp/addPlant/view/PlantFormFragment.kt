package com.switcherette.plantapp.addPlant.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.switcherette.plantapp.R
import com.switcherette.plantapp.databinding.FragmentPlantFormBinding
import com.switcherette.plantapp.databinding.FragmentSearchByPictureBinding

class PlantFormFragment : Fragment(R.layout.fragment_plant_form) {

    private lateinit var binding: FragmentPlantFormBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentPlantFormBinding.bind(view)
    }
}