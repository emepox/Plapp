package com.switcherette.plantapp.utils

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.widget.Toast
import java.text.SimpleDateFormat
import java.util.*


class WaterAlarm(private val context: Context){
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
    fun isAlarmSet(): Boolean{
        val intent = Intent(context, WaterAlarmReceiver::class.java)
        return PendingIntent.getBroadcast(context, 0, intent,0) != null
    }

}