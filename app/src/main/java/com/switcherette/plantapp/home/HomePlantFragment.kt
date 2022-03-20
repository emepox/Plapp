package com.switcherette.plantapp.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import com.switcherette.plantapp.R
import com.switcherette.plantapp.addPlant.viewModel.SearchByPictureViewModel
import com.switcherette.plantapp.databinding.FragmentHomePlantBinding
import com.switcherette.plantapp.databinding.FragmentSearchByPictureBinding
import com.switcherette.plantapp.room.AppDB
import org.koin.androidx.viewmodel.ext.android.viewModel


class HomePlantFragment : Fragment(R.layout.fragment_home_plant) {

    private val homePlantVM: HomePlantViewModel by viewModel()
    private lateinit var binding: FragmentHomePlantBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentHomePlantBinding.bind(view)

        homePlantVM.getRandomQuote()
        homePlantVM.quote.observe(viewLifecycleOwner){
            binding.tvHomePlantFact.text = it.q
            binding.tvHomePlantFactAuthor.text = it.a
        }


    }
}