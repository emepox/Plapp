package com.switcherette.plantapp.calendar.view

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.applandeo.materialcalendarview.EventDay
import com.applandeo.materialcalendarview.listeners.OnDayClickListener
import com.switcherette.plantapp.R
import com.switcherette.plantapp.calendar.adapter.TasksAdapter
import com.switcherette.plantapp.calendar.viewModel.CalendarViewModel
import com.switcherette.plantapp.data.WaterEvent
import com.switcherette.plantapp.databinding.FragmentCalendarBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.component.KoinComponent
import java.text.SimpleDateFormat
import java.util.*

class CalendarFragment : Fragment(R.layout.fragment_calendar), KoinComponent {

    private lateinit var binding: FragmentCalendarBinding
    private val calendarVM: CalendarViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentCalendarBinding.bind(view)

        calendarVM.getWaterEvents()

        binding.rvCalendarTasksPerDay.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        observeAllWaterEvents()

        observeWaterEventsPerDay()

        onDateClick()
    }

    private fun onDateClick() {
        binding.calendarView.setOnDayClickListener(object : OnDayClickListener {
            override fun onDayClick(eventDay: EventDay) {
                val millis = convertEventDayToMillis(eventDay)
                setHeadingForDay(millis)
                calendarVM.getWaterEventByTimeRange(startTime(eventDay), endTime(eventDay))
            }

            private fun convertEventDayToMillis(eventDay: EventDay): Long {
                val c = Calendar.getInstance()
                c.time = eventDay.calendar.time
                return c.timeInMillis
            }

            private fun setHeadingForDay(millis: Long) {
                val sdf = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
                val dateSimpleDayFormat = sdf.format(millis)
                binding.tvSelectedDate.text = dateSimpleDayFormat
            }

            private fun startTime(eventDay: EventDay): Long {
                return eventDay.calendar.let {
                    it[Calendar.HOUR_OF_DAY] = 1
                    it[Calendar.MINUTE] = 0
                    it[Calendar.SECOND] = 0
                    it[Calendar.MILLISECOND] = 0
                    it.timeInMillis
                }
            }

            private fun endTime(eventDay: EventDay): Long {
                return eventDay.calendar.let {
                    it[Calendar.HOUR_OF_DAY] = 23
                    it[Calendar.MINUTE] = 0
                    it[Calendar.SECOND] = 0
                    it[Calendar.MILLISECOND] = 0
                    it.timeInMillis
                }
            }
        })
    }

    private fun observeWaterEventsPerDay() {
        calendarVM.waterEventsPerDay.observe(viewLifecycleOwner) { events ->
            binding.rvCalendarTasksPerDay.adapter = TasksAdapter(events)
        }
    }

    private fun observeAllWaterEvents() {
        calendarVM.allWaterEvents.observe(viewLifecycleOwner) { events ->
            if (events != null) {
                fillCalendarWithEvents(convertEventsToEventDays(events))
            }
        }
    }

    private fun convertEventsToEventDays(events: List<WaterEvent>): List<EventDay> {
        return events.map {
            val calendar = Calendar.getInstance().apply {
                time = Date(it.repeatStart)
            }
            EventDay(calendar, R.drawable.ic_flower)
        }
    }

    private fun fillCalendarWithEvents(events: List<EventDay>) {
        binding.calendarView.apply {
            setEvents(events)
        }
    }
}