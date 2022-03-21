package com.switcherette.plantapp.calendar.view

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.applandeo.materialcalendarview.EventDay
import com.applandeo.materialcalendarview.listeners.OnDayClickListener
import com.switcherette.plantapp.R
import com.switcherette.plantapp.calendar.viewModel.CalendarViewModel
import com.switcherette.plantapp.data.WaterEvent
import com.switcherette.plantapp.databinding.FragmentCalendarBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.text.SimpleDateFormat
import java.util.*

class CalendarFragment: Fragment(R.layout.fragment_calendar){

    private lateinit var binding: FragmentCalendarBinding
    private val calendarVM: CalendarViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentCalendarBinding.bind(view)

   /*     calendarVM.getWaterEvents()

        calendarVM.waterEvents.observe(viewLifecycleOwner) { events ->
            if (events != null) {
                fillCalendarWithEvents(convertEventsToEventDays(events))

            } else {
                Toast.makeText(requireContext(), "Http error", Toast.LENGTH_SHORT).show()
            }
        }*/

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

    private fun convertEventsToEventDays(events: List<WaterEvent>): List<EventDay> {
        return events.map {
            val calendar = Calendar.getInstance().apply {
                time = Date(it.repeatStart)
            }
            EventDay(calendar, R.drawable.ic_waterdrop)
        }
    }

    private fun fillCalendarWithEvents(events: List<EventDay>) {
        binding.calendarView.apply {
            setEvents(events)
            setOnDayClickListener(object : OnDayClickListener {
                override fun onDayClick(eventDay: EventDay) {
                    val c = Calendar.getInstance()
                    c.time = eventDay.calendar.time
                   // val millis = c.timeInMillis
                }
            })
        }
    }

}