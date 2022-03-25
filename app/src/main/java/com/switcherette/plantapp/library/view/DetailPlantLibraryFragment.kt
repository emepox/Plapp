package com.switcherette.plantapp.library.view

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.view.View.VISIBLE
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.google.android.material.transition.MaterialFadeThrough
import com.switcherette.plantapp.R
import com.switcherette.plantapp.data.PlantInfo
import com.switcherette.plantapp.databinding.FragmentDetailLibraryPlantBinding

class DetailPlantLibraryFragment : Fragment(R.layout.fragment_detail_library_plant) {

    private lateinit var binding: FragmentDetailLibraryPlantBinding
    private lateinit var plant: PlantInfo

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().findViewById<ConstraintLayout>(R.id.cl_mainActivity).setBackgroundColor(
            Color.parseColor("#F3F4F6")
        )

        binding = FragmentDetailLibraryPlantBinding.bind(view)
        enterTransition = MaterialFadeThrough()
        exitTransition = MaterialFadeThrough()

        val args: DetailPlantLibraryFragmentArgs by navArgs()
        plant = args.libraryPlant!!


        with(binding) {

            Glide.with(requireContext())
                .load(plant.img)
                .placeholder(R.drawable.plant_img)
                .centerCrop()
                .into(ivDetailPicture)

            etCareWatering.setText(getString(R.string.every_x_days, plant.water))
            etDetailNickname.setText(plant.scientificName)
            tvDetailCommonName.text = plant.commonName?: "No common name data"

            plant.light.let {
                when(it) {
                    1 -> {
                        etCareLight.setText(R.string.full_shadow)
                        ivCareLight.setImageResource(R.drawable.ic_cloud)
                    }
                    2 -> {
                        etCareLight.setText(R.string.partial_shade)
                        ivCareLight.setImageResource(R.drawable.ic_sun_clouds)
                    }
                    3 -> {
                        etCareLight.setText(R.string.full_sun)
                        ivCareLight.setImageResource(R.drawable.ic_sun)
                    }
                    4 -> {
                        etCareLight.setText(R.string.light_adaptable)
                        ivCareLight.setImageResource(R.drawable.ic_sun_clouds)
                    }
                    else -> etCareLight.setText(R.string.no_data_available)

                }
            }
            etDetailDescription.setText(plant.description?: getString(R.string.no_description_data_available))
            etDetailCultivation.setText(plant.cultivation?: getString(R.string.no_description_data_available))
            plant.family?.let { family ->
                tvDetailScientificName.text = family
                tvDetailScientificName.visibility = VISIBLE
            }
        }
    }
}