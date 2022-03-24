package com.switcherette.plantapp.detail

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import android.view.View.VISIBLE
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.setPadding
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.transition.MaterialFadeThrough
import com.switcherette.plantapp.R
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
        requireActivity().findViewById<ConstraintLayout>(R.id.cl_mainActivity).setBackgroundColor(
            Color.parseColor("#F3F4F6")
        )

        binding = FragmentDetailPlantBinding.bind(view)
        enterTransition = MaterialFadeThrough()
        exitTransition = MaterialFadeThrough()

        plant = arguments?.getParcelable("plant")!!

        setOptionsAnimation()
        detailPlantVM.getDaysToWater(plant)

        with(binding) {

            detailPlantVM.daysToWater.observe(viewLifecycleOwner) {
                tvWateringTime.text = "$it"
            }

            Glide.with(requireContext())
                .load(plant.image)
                .placeholder(R.drawable.plant_img)
                .centerCrop()
                .into(ivDetailPicture);


            etDetailNickname.setText(plant.nickname)
            tvDetailCommonName.text = plant.commonName?: "No common name data"

            etCareWatering.setText("Every ${plant.water} days")
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
            tvDetailScientificName.text = plant.scientificName?: "No data available"
            etDetailDescription.setText(plant.description?: "No description data available!")
            etDetailCultivation.setText(plant.cultivation?: "No cultivation data available!")
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
            saveIcon = !saveIcon
            if (saveIcon) {
                // EDIT FUNCTIONALITY
                editPlant()
            } else {
                // SAVE FUNCTIONALITY
                saveUpdatedPlant()
            }
        }

        btnDeletePlant.setOnClickListener {
            setDialogBox()
        }
    }

    private fun saveUpdatedPlant() {
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
            detailPlantVM.editPlant(editedPlant)
            detailPlantVM.editPlantOnFirebase(editedPlant)
            onEditButtonClicked()

            with(binding) {
                mutableListOf(
                    etDetailNickname,
                    etDetailDescription,
                    etDetailCultivation
                ).onEach { element ->
                    element.isEnabled = false
                    element.setBackgroundResource(0)
                    element.setPadding(0)
                }
            }

            Toast.makeText(requireContext(), "Your plant info has been updated", Toast.LENGTH_SHORT)
                .show()
        }
    }

    private fun editPlant() {
        Toast.makeText(requireContext(), "Please edit", Toast.LENGTH_SHORT).show()
        btnEditPlant.setImageResource(R.drawable.ic_save)

        with(binding) {
            mutableListOf(
                etDetailNickname,
                etDetailDescription,
                etDetailCultivation
            ).onEach { element ->
                element.isEnabled = true
                element.setBackgroundResource(R.drawable.rounded2)
                element.setPadding(25)
            }
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

    private fun setDialogBox() {
        val singleItems = arrayOf("It did not survive :( ", "${plant.nickname} found a new home", "I added the wrong plant", "No comment")
        val checkedItem = 1

       MaterialAlertDialogBuilder(requireContext())
            .setTitle("Oh no! Can we know why you are deleting ${plant.nickname}?")
            .setNeutralButton("delete") { dialog, which ->
                detailPlantVM.deletePlant(plant)
                detailPlantVM.deletePlantFromFirebase(plant)
                detailPlantVM.deleteWaterEvent(plant)
                findNavController().navigate(R.id.action_detailPlantFragment_to_myPlantsFragment)
            }
            .setPositiveButton("cancel") { dialog, which ->
            }
            .setSingleChoiceItems(singleItems, checkedItem) { dialog, which ->
            }
            .show()
    }

    private fun setClickable(clicked: Boolean) {
        btnEditPlant.isClickable = !clicked
        btnDeletePlant.isClickable = !clicked
    }

}