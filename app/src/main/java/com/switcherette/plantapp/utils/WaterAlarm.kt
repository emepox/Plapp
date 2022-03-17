package com.switcherette.plantapp.utils

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import java.text.SimpleDateFormat
import java.util.*


class WaterAlarm: KoinComponent {
    private val context: Context by inject()
    private lateinit var alarmManager: AlarmManager
    private lateinit var pendingIntent: PendingIntent

    fun createAlarm(start: Long) {
        alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, WaterAlarmReceiver::class.java)

        pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0)

        alarmManager.set(
            AlarmManager.RTC_WAKEUP,
            start,
            pendingIntent
        )
        val sdf = SimpleDateFormat("dd.MM.yyyy, HH:mm", Locale.getDefault())
        val date = sdf.format(Date(start))
        Toast.makeText(context, "Alarm set to $date", Toast.LENGTH_LONG).show()
    }

    fun cancelAlarm(){
        alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, WaterAlarmReceiver::class.java)
        pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0)
        alarmManager.cancel(pendingIntent)
    }
    fun checkIfNotSet(): Boolean{
        val intent = Intent(context, WaterAlarmReceiver::class.java)
        val alarmUp = PendingIntent.getBroadcast(context, 0, intent,0) != null
        if (alarmUp) {
            return false
        }
        return true
    }

}