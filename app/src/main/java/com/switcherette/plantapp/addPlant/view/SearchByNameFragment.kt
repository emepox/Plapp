package com.switcherette.plantapp.addPlant.view

import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.activity.addCallback
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.switcherette.plantapp.R
import com.switcherette.plantapp.addPlant.adapter.SearchByNamePagingAdapter
import com.switcherette.plantapp.addPlant.viewModel.SearchByNameViewModel
import com.switcherette.plantapp.data.PlantInfo
import com.switcherette.plantapp.data.UserPlant
import com.switcherette.plantapp.databinding.FragmentSearchByNameBinding
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*


class SearchByNameFragment : Fragment(R.layout.fragment_search_by_name) {

    private lateinit var binding: FragmentSearchByNameBinding

    private val viewModel: SearchByNameViewModel by viewModel()
    private var userPhotoUrl: String? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSearchByNameBinding.bind(view)
        requireActivity().onBackPressedDispatcher.addCallback(this) {}
        requireActivity().findViewById<ConstraintLayout>(R.id.cl_mainActivity).setBackgroundColor(
            ResourcesCompat.getColor(resources, R.color.white, null)
        )

        userPhotoUrl = arguments?.getString("userPhotoUrl")

        setRecyclerView()
        binding.tvNoList.setOnClickListener { createEmptyUserPlant() }
    }

    private fun setRecyclerView() {
        val recyclerView = binding.rvSearchPlantByName

        recyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        val allPlantsAdapter = SearchByNamePagingAdapter() { choosePlant(it) }
        recyclerView.adapter = allPlantsAdapter

        lifecycleScope.launch {
            viewModel.pagingDataFlow.collectLatest(allPlantsAdapter::submitData)
        }

        binding.etSearch.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_GO) {
                binding.etSearch.text.trim().let {
                    setNewFlow(it, allPlantsAdapter)
                }
                true
            } else {
                false
            }
        }
        binding.etSearch.setOnKeyListener { _, keyCode, event ->
            if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                binding.etSearch.text.trim().let {
                    setNewFlow(it, allPlantsAdapter)
                }
                true
            } else {
                false
            }
        }
        val notLoading = allPlantsAdapter.loadStateFlow
            .distinctUntilChangedBy { it.source.refresh }
            .map { it.source.refresh is LoadState.NotLoading }

        lifecycleScope.launch {
            notLoading.collect {
                if (it) binding.rvSearchPlantByName.scrollToPosition(0)
            }
        }
    }

    private fun setNewFlow(
        it: CharSequence,
        allPlantsAdapter: SearchByNamePagingAdapter
    ): Job {
        viewModel.update(it.toString())
        return lifecycleScope.launch {
            viewModel.pagingDataFlow.collectLatest {
                allPlantsAdapter.submitData(it)
                binding.rvSearchPlantByName.scrollTo(0, 0)
            }
        }
    }

    private fun choosePlant(plantInfo: PlantInfo) {
        val image = if (userPhotoUrl != null) {
            userPhotoUrl
        } else {
            plantInfo.img
        }

        val userPlant = UserPlant(
            UUID.randomUUID().toString(),
            "",
            plantInfo.scientificName,
            plantInfo.commonName,
            plantInfo.family,
            plantInfo.description,
            plantInfo.cultivation,
            plantInfo.light ?: 2,
            plantInfo.water ?: 15,
            image,
            Firebase.auth.currentUser?.uid.orEmpty()
        )

        val action =
            SearchByNameFragmentDirections.actionSearchByNameFragmentToPlantForm1Fragment(userPlant)
        findNavController().navigate(action)
    }

    private fun createEmptyUserPlant() {
        findNavController().navigate(R.id.action_searchByNameFragment_to_plantForm1Fragment)
    }
}

