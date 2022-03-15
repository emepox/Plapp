package com.switcherette.plantapp.calendar

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import com.switcherette.plantapp.databinding.FragmentCalendarBinding

class CalendarFragment : Fragment() {

    private lateinit var binding: FragmentCalendarBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentCalendarBinding.bind(view)
    }
}