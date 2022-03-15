package com.switcherette.plantapp.addPlant.view

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.switcherette.plantapp.R
import com.switcherette.plantapp.databinding.FragmentHowToIdentifyBinding


class HowToIdentifyFragment : Fragment(R.layout.fragment_how_to_identify) {

    private lateinit var binding: FragmentHowToIdentifyBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentHowToIdentifyBinding.bind(view)

        binding.ivCameraIcon.setOnClickListener {
            findNavController().navigate(R.id.action_howToIdentifyFragment_to_searchByPictureFragment)
        }

        binding.ivSearchIcon.setOnClickListener {
            findNavController().navigate(R.id.action_howToIdentifyFragment_to_searchByNameFragment)
        }
    }
}