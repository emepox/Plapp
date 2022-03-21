package com.switcherette.plantapp.detail

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
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

        Log.e("myplant", plant.toString())

    }
}