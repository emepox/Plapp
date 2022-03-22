package com.switcherette.plantapp.detail

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import android.view.View.VISIBLE
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.switcherette.plantapp.R
import com.switcherette.plantapp.addPlant.viewModel.AddPlantPictureViewModel
import com.switcherette.plantapp.data.UserPlant
import com.switcherette.plantapp.databinding.FragmentDetailPlantBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailPlantFragment : Fragment(R.layout.fragment_detail_plant) {

    private val detailPlantVM: DetailPlantViewModel by viewModel()

    private lateinit var binding: FragmentDetailPlantBinding
    private lateinit var plant: UserPlant

    private var saveIcon : Boolean = false

    private val rotateOpen: Animation by lazy {
        AnimationUtils.loadAnimation(
            requireContext(),
            R.anim.rotate_open_anim
        )
    }
    private val rotateClose: Animation by lazy {
        AnimationUtils.loadAnimation(
            requireContext(),
            R.anim.rotate_close_anim
        )
    }
    private val fromBottom: Animation by lazy {
        AnimationUtils.loadAnimation(
            requireContext(),
            R.anim.from_bottom_anim
        )
    }
    private val toBottom: Animation by lazy {
        AnimationUtils.loadAnimation(
            requireContext(),
            R.anim.to_bottom_anim
        )
    }
    private var clicked: Boolean = false
    private lateinit var btnEdit: FloatingActionButton
    private lateinit var btnEditPlant: FloatingActionButton
    private lateinit var btnDeletePlant: FloatingActionButton

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentDetailPlantBinding.bind(view)

        plant = arguments?.getParcelable("plant")!!

        Log.e("myplant", "${plant.image}")

        setOptionsAnimation()

        with(binding) {


            tvDetailNickname.text = plant.nickname
            tvDetailCommonName.text = plant.commonName?: "No common name data"

            tvCareWatering.text = "every ${plant.water} days"
            plant.light.let {
                when(it) {
                    1 -> {
                        tvCareLight.text = "Full shadow"
                        ivCareLight.setImageResource(R.drawable.ic_cloud)
                    }
                    2 -> {
                        tvCareLight.text = "Partial shade"
                        ivCareLight.setImageResource(R.drawable.ic_sun_clouds)
                    }
                    3 -> {
                        tvCareLight.text= "Full sun"
                        ivCareLight.setImageResource(R.drawable.ic_sun)
                    }
                    4 -> {
                        tvCareLight.text = "Light adaptable"
                        ivCareLight.setImageResource(R.drawable.ic_sun_clouds)
                    }
                    else -> tvCareLight.text = "No data"

                }
            }
            tvDetailScientificName.text = plant.scientificName?: "No data"
            tvDetailDescription.text = plant.description?: "No description data"
            tvDetailCultivation.text = plant.cultivation?: "No cultivation data"
            plant.family?.let { family ->
                tvDetailFamily.text = family
                tvDetailFamily.visibility = VISIBLE
            }
        }
    }

    // MOVIDA DE LOS BOTONES

    private fun setOptionsAnimation() {
        btnEdit = binding.btnEdit
        btnEditPlant = binding.btnEditPlant
        btnDeletePlant = binding.btnDeletePlant

        btnEdit.setOnClickListener {
            onEditButtonClicked()
        }


        btnEditPlant.setOnClickListener {
            Log.e("saveiconbefore", "$saveIcon")
            saveIcon = !saveIcon
            Log.e("saveiconafter", "$saveIcon")
            if (saveIcon) {

                // EDIT FUNCTIONALITY
                Toast.makeText(requireContext(), "Clicked edit", Toast.LENGTH_SHORT).show()
                btnEditPlant.setImageResource(R.drawable.ic_save)
            } else {
                // SAVE FUNCTIONALITY
                Toast.makeText(requireContext(), "Clicked save", Toast.LENGTH_SHORT).show()

                btnEditPlant.setImageResource(R.drawable.ic_edit2)
            }
        }

        btnDeletePlant.setOnClickListener {
            detailPlantVM.deletePlant(plant)
            findNavController().navigate(R.id.action_detailPlantFragment_to_myPlantsFragment)

        }


    }

    private fun onEditButtonClicked() {
        setVisibility(clicked)
        setAnimation(clicked)
        setClickable(clicked)
        clicked = !clicked
    }

    private fun setVisibility(clicked: Boolean) {
        if (!clicked) {
            btnEditPlant.visibility = View.VISIBLE
            btnDeletePlant.visibility = View.VISIBLE
        } else {
            btnEditPlant.visibility = View.INVISIBLE
            btnDeletePlant.visibility = View.INVISIBLE
        }
    }

    private fun setAnimation(clicked: Boolean) {
        if (!clicked) {
            btnEdit.startAnimation(rotateOpen)
            btnEditPlant.startAnimation(fromBottom)
            btnDeletePlant.startAnimation(fromBottom)
        } else {
            btnEdit.startAnimation(rotateClose)
            btnEditPlant.startAnimation(toBottom)
            btnDeletePlant.startAnimation(toBottom)
        }
    }

    private fun setClickable(clicked: Boolean) {
        btnEditPlant.isClickable = !clicked
        btnDeletePlant.isClickable = !clicked
    }

}