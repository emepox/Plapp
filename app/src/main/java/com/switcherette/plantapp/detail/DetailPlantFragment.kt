package com.switcherette.plantapp.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import com.switcherette.plantapp.databinding.FragmentDetailPlantBinding

class DetailPlantFragment : Fragment() {

    private lateinit var binding: FragmentDetailPlantBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentDetailPlantBinding.bind(view)
    }
}