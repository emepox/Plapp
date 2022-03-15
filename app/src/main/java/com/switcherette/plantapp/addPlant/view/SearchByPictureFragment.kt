package com.switcherette.plantapp.addPlant.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.switcherette.plantapp.R
import com.switcherette.plantapp.databinding.FragmentSearchByNameBinding
import com.switcherette.plantapp.databinding.FragmentSearchByPictureBinding

class SearchByPictureFragment : Fragment(R.layout.fragment_search_by_picture) {

    private lateinit var binding: FragmentSearchByPictureBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentSearchByPictureBinding.bind(view)
    }
}