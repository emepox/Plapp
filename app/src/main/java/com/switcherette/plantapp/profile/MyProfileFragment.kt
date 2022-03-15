package com.switcherette.plantapp.profile

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.switcherette.plantapp.R
import com.switcherette.plantapp.databinding.FragmentMyProfileBinding
import com.switcherette.plantapp.databinding.FragmentPlantFormBinding

class MyProfileFragment : Fragment(R.layout.fragment_my_profile) {

    private lateinit var binding: FragmentMyProfileBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentMyProfileBinding.bind(view)
    }
}