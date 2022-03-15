package com.switcherette.plantapp.addPlant.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.switcherette.plantapp.R
import com.switcherette.plantapp.databinding.FragmentHowToIdentifyBinding
import com.switcherette.plantapp.databinding.FragmentMyPlantsBinding


class HowToIdentifyFragment : Fragment(R.layout.fragment_how_to_identify) {

    private lateinit var binding: FragmentHowToIdentifyBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentHowToIdentifyBinding.bind(view)
    }
}