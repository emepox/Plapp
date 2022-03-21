package com.switcherette.plantapp.myPlants.view

import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.switcherette.plantapp.R
import com.switcherette.plantapp.data.UserPlant
import com.switcherette.plantapp.databinding.FragmentMyPlantsBinding
import com.switcherette.plantapp.myPlants.adapter.MyPlantsAdapter
import com.switcherette.plantapp.myPlants.viewModel.MyPlantsViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MyPlantsFragment : Fragment(R.layout.fragment_my_plants) {

    private lateinit var binding: FragmentMyPlantsBinding
    private val myPlantsVM: MyPlantsViewModel by viewModel()

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
    private lateinit var btnAddPlant: FloatingActionButton
    private lateinit var btnSearchByName: FloatingActionButton
    private lateinit var btnSearchByPhoto: FloatingActionButton

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMyPlantsBinding.bind(view)

        myPlantsVM.getUserPlants()
        setRecyclerView()
        setOptionsAnimation()
    }

    private fun setRecyclerView() {
        val recyclerView = binding.rvMyPlants

        recyclerView.layoutManager =
            GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)

        myPlantsVM.userPlants.observe(viewLifecycleOwner) {
            val myPlantsAdapter = MyPlantsAdapter(it) { showPlantDetails() }
            recyclerView.adapter = myPlantsAdapter
        }

    }

    private fun showPlantDetails() {
        TODO("Not yet implemented")
    }

    private fun setOptionsAnimation() {
        btnAddPlant = binding.btnAddPlant
        btnSearchByName = binding.btnSearchByName
        btnSearchByPhoto = binding.btnSearchByPhoto

        btnAddPlant.setOnClickListener {
            onAddPlantButtonClicked()
        }

        btnSearchByName.setOnClickListener {
            findNavController().navigate(R.id.action_myPlantsFragment_to_searchByNameFragment)
        }

        btnSearchByPhoto.setOnClickListener {
            findNavController().navigate(R.id.action_myPlantsFragment_to_addPlantPictureFragment)
        }
    }

    private fun onAddPlantButtonClicked() {
        setVisibility(clicked)
        setAnimation(clicked)
        setClickable(clicked)
        clicked = !clicked
    }

    private fun setVisibility(clicked: Boolean) {
        if (!clicked) {
            btnSearchByName.visibility = View.VISIBLE
            btnSearchByPhoto.visibility = View.VISIBLE
        } else {
            btnSearchByName.visibility = View.INVISIBLE
            btnSearchByPhoto.visibility = View.INVISIBLE
        }
    }

    private fun setAnimation(clicked: Boolean) {
        if (!clicked) {
            btnAddPlant.startAnimation(rotateOpen)
            btnSearchByName.startAnimation(fromBottom)
            btnSearchByPhoto.startAnimation(fromBottom)
        } else {
            btnAddPlant.startAnimation(rotateClose)
            btnSearchByName.startAnimation(toBottom)
            btnSearchByPhoto.startAnimation(toBottom)
        }
    }

    private fun setClickable(clicked: Boolean) {
        btnSearchByName.isClickable = !clicked
        btnSearchByPhoto.isClickable = !clicked
    }
}