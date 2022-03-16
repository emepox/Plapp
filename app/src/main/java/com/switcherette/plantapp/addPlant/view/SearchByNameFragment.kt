package com.switcherette.plantapp.addPlant.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.switcherette.plantapp.R
import com.switcherette.plantapp.databinding.FragmentHowToIdentifyBinding
import com.switcherette.plantapp.databinding.FragmentSearchByNameBinding


class SearchByNameFragment : Fragment(R.layout.fragment_search_by_name) {

    private lateinit var binding: FragmentSearchByNameBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentSearchByNameBinding.bind(view)
    }
}