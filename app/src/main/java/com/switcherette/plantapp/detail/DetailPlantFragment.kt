package com.switcherette.plantapp.detail

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import android.view.View.VISIBLE
import android.widget.TextView
import androidx.core.view.isVisible
import com.switcherette.plantapp.R
import com.switcherette.plantapp.data.UserPlant
import com.switcherette.plantapp.databinding.FragmentDetailPlantBinding

class DetailPlantFragment : Fragment(R.layout.fragment_detail_plant) {

    private lateinit var binding: FragmentDetailPlantBinding
    private lateinit var plant: UserPlant


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentDetailPlantBinding.bind(view)

        plant = arguments?.getParcelable("plant")!!

        Log.e("myplant", plant.family.toString())

        with(binding) {

            tvDetailNickname.text = plant.nickname
            tvDetailCommonName.text = plant.commonName?: "No common name data"
            tvCareWatering.text = plant.water.toString()
            tvCareLight.text = plant.light.toString()
            tvDetailScientificName.text = plant.scientificName?: "No data"
            tvDetailDescription.text = plant.description?: "No description data"
            tvDetailCultivation.text = plant.cultivation?: "No cultivation data"
            plant.family?.let { family ->
                tvDetailFamily.text = family
                tvDetailFamily.visibility = VISIBLE
            }
        }
    }
}