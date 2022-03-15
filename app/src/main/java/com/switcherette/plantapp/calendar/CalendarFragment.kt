package com.switcherette.plantapp.calendar

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.switcherette.plantapp.R
import com.switcherette.plantapp.databinding.FragmentCalendarBinding

class CalendarFragment: Fragment(R.layout.fragment_calendar){

    private lateinit var binding: FragmentCalendarBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentCalendarBinding.bind(view)


    }
}