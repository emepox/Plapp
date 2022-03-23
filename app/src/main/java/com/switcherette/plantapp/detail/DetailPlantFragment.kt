package com.switcherette.plantapp.detail

import android.graphics.Color
import android.os.Bundle
import android.text.Editable
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
    private lateinit var editedPlant : UserPlant
    private lateinit var daysLeftToWater : String

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

        setOptionsAnimation()
        detailPlantVM.getWaterEvent(plant)

        with(binding) {

            detailPlantVM.daysToWater.observe(viewLifecycleOwner) {
                tvWateringTime.text = "$it"
            }

            etDetailNickname.setText(plant.nickname)
            tvDetailCommonName.text = plant.commonName?: "No common name data"

            etCareWatering.setText("every ${plant.water} days")
            plant.light.let {
                when(it) {
                    1 -> {
                        etCareLight.setText("Full shadow")
                        ivCareLight.setImageResource(R.drawable.ic_cloud)
                    }
                    2 -> {
                        etCareLight.setText("Partial shade")
                        ivCareLight.setImageResource(R.drawable.ic_sun_clouds)
                    }
                    3 -> {
                        etCareLight.setText("Full sun")
                        ivCareLight.setImageResource(R.drawable.ic_sun)
                    }
                    4 -> {
                        etCareLight.setText("Light adaptable")
                        ivCareLight.setImageResource(R.drawable.ic_sun_clouds)
                    }
                    else -> etCareLight.setText("No data")

                }
            }
            tvDetailScientificName.text = plant.scientificName?: "No data"
            etDetailDescription.setText(plant.description?: "No description data")
            etDetailCultivation.setText(plant.cultivation?: "No cultivation data")
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
                Toast.makeText(requireContext(), "Please edit", Toast.LENGTH_SHORT).show()
                btnEditPlant.setImageResource(R.drawable.ic_save)

                with(binding) {
                    mutableListOf(
                        etDetailNickname,
                        etDetailDescription,
                        etDetailCultivation
                    ).onEach { element ->
                        element.isEnabled = true
                        element.setBackgroundColor(Color.parseColor("#FF9C4141"))
                        //it.setHintTextColor("#FF9C4141")
                        //it.setTextAppearance(R.style.LineEditText)
                    }
                }
            } else {
                // SAVE FUNCTIONALITY
                Toast.makeText(requireContext(), "Clicked save", Toast.LENGTH_SHORT).show()
                btnEditPlant.setImageResource(R.drawable.ic_edit2)

                // Create new edited plant object
                with(binding) {
                    editedPlant = UserPlant(
                        plant.id,
                        etDetailNickname.text.toString(),
                        plant.scientificName,
                        plant.commonName,
                        plant.family,
                        etDetailDescription.text.toString(),
                        etDetailCultivation.text.toString(),
                        plant.light,
                        plant.water,
                        plant.image,
                        plant.userId
                    )
                    Log.e("editedplant", "$editedPlant")
                    detailPlantVM.editPlant(editedPlant)
                    detailPlantVM.editPlantOnFirebase(editedPlant)
                }
            }
        }

        btnDeletePlant.setOnClickListener {
            detailPlantVM.deletePlant(plant)
            detailPlantVM.deletePlantFromFirebase(plant)
            detailPlantVM.deleteWaterEvent(plant)
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