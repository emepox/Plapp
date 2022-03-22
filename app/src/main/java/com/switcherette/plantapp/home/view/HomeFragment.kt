package com.switcherette.plantapp.home.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.switcherette.plantapp.R
import com.switcherette.plantapp.databinding.FragmentHomePlantBinding
import com.switcherette.plantapp.home.viewModel.HomeViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


class HomeFragment : Fragment(R.layout.fragment_home_plant) {

    private val homeVM: HomeViewModel by viewModel()
    private lateinit var binding: FragmentHomePlantBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentHomePlantBinding.bind(view)

        homeVM.getRandomQuote()
        homeVM.quote.observe(viewLifecycleOwner){
            binding.tvHomePlantFact.text = it.q
            binding.tvHomePlantFactAuthor.text = it.a
        }
        binding.tvHomeHeading.text = "WELCOME, ${Firebase.auth.currentUser?.displayName?.uppercase()}" ?: resources.getString(R.string.welcome_stranger)


    }
}