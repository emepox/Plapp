package com.switcherette.plantapp.profile

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.firebase.ui.auth.AuthUI
import com.switcherette.plantapp.R
import com.switcherette.plantapp.databinding.FragmentMyProfileBinding
import com.switcherette.plantapp.databinding.FragmentPlantFormBinding

class MyProfileFragment : Fragment(R.layout.fragment_my_profile) {

    private lateinit var binding: FragmentMyProfileBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentMyProfileBinding.bind(view)

        with(binding) {
            btnSignOut.setOnClickListener {
                AuthUI.getInstance()
                    .signOut(requireContext())
                    .addOnCompleteListener {
                        Toast.makeText(requireContext(), "Signed out", Toast.LENGTH_SHORT).show()
                        findNavController().navigate(R.id.action_myProfileFragment_to_loginFragment)
                    }

            }
        }
    }


}