package com.switcherette.plantapp.utils

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.widget.Toast
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import java.util.*

class TestNotification {
    private lateinit var picker: MaterialTimePicker
    private lateinit var calendar: Calendar
    private lateinit var alarmManager: AlarmManager
    private lateinit var pendingIntent: PendingIntent

//    private fun createAlarm() {
//        alarmManager = requireContext().getSystemService(Context.ALARM_SERVICE) as AlarmManager
//        val intent = Intent(context, WaterAlarmReceiver::class.java)
//
//        pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0)
//
//        alarmManager.setInexactRepeating(
//            AlarmManager.RTC_WAKEUP,
//            calendar.timeInMillis,
//            AlarmManager.INTERVAL_DAY * 5,
//            pendingIntent
//        )
//        Toast.makeText(requireContext(), "Alarm set to ${calendar.time}", Toast.LENGTH_LONG).show()
//    }
//
//    private fun setTime() {
//        picker = MaterialTimePicker.Builder()
//            .setTimeFormat(TimeFormat.CLOCK_24H)
//            .setHour(12)
//            .setMinute(0)
//            .setTitleText("Select Time")
//            .build()
//        picker.show(parentFragmentManager, "yeet")
//        picker.addOnPositiveButtonClickListener {
//            calendar = Calendar.getInstance()
//            calendar[Calendar.SECOND] += 15
//        }
//    }
}