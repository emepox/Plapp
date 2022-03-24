package com.switcherette.plantapp.home.view

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.switcherette.plantapp.R
import com.switcherette.plantapp.calendar.adapter.TasksAdapter
import com.switcherette.plantapp.databinding.FragmentHomePlantBinding
import com.switcherette.plantapp.home.adapter.HomeTasksAdapter
import com.switcherette.plantapp.home.viewModel.HomeViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*


class HomeFragment : Fragment(R.layout.fragment_home_plant) {

    private val homeVM: HomeViewModel by viewModel()
    private lateinit var binding: FragmentHomePlantBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentHomePlantBinding.bind(view)

        homeVM.getRandomQuote()
        homeVM.quote.observe(viewLifecycleOwner) {
            binding.tvHomePlantFact.text = it.q
            binding.tvHomePlantFactAuthor.text = it.a
        }

        val name = Firebase.auth.currentUser?.displayName?.split(" ")?.get(0)?.uppercase()

        binding.tvHomeHeading.text =
            "WELCOME, $name"
                ?: resources.getString(R.string.welcome_stranger)

        val startTime= Calendar.getInstance().let {
            it[Calendar.HOUR_OF_DAY] = 1
            it[Calendar.MINUTE] = 0
            it[Calendar.SECOND] = 0
            it[Calendar.MILLISECOND] = 0
            it.timeInMillis
        }

        val endTime= Calendar.getInstance().let {
            it[Calendar.HOUR_OF_DAY] = 23
            it[Calendar.MINUTE] = 0
            it[Calendar.SECOND] = 0
            it[Calendar.MILLISECOND] = 0
            it.timeInMillis
        }

        observeTodaysWaterEvents()

        homeVM.getTodaysWaterEventsByTimeRange(startTime, endTime)

        binding.rvHomeList.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

    }

    private fun observeTodaysWaterEvents() {
        homeVM.waterEventsToday.observe(viewLifecycleOwner) { events ->
            if (events != null) {

                binding.rvHomeList.adapter = HomeTasksAdapter(events)
            }
        }
    }
}