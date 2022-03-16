package com.switcherette.plantapp.calendar

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.applandeo.materialcalendarview.EventDay
import com.applandeo.materialcalendarview.listeners.OnDayClickListener
import com.switcherette.plantapp.R
import com.switcherette.plantapp.databinding.FragmentCalendarBinding
import java.text.SimpleDateFormat
import java.util.*

class CalendarFragment: Fragment(R.layout.fragment_calendar){

    private lateinit var binding: FragmentCalendarBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentCalendarBinding.bind(view)

        binding.calendarView.setOnDayClickListener(object : OnDayClickListener {
            override fun onDayClick(eventDay: EventDay) {
                val c = Calendar.getInstance()
                c.time = eventDay.calendar.time
                val millis = c.timeInMillis

                val sdf = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
                val dateSimpleDayFormat = sdf.format(millis)

                binding.tvSelectedDate.setText(dateSimpleDayFormat)
            }
        })




    }

}