package com.switcherette.plantapp.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import com.switcherette.plantapp.R
import com.switcherette.plantapp.databinding.FragmentHomePlantBinding


class HomePlantFragment : Fragment(R.layout.fragment_home_plant) {
    private lateinit var binding: FragmentHomePlantBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentHomePlantBinding.bind(view)
    }
}