package com.switcherette.plantapp.utils

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import java.text.SimpleDateFormat
import java.util.*


class WaterAlarm(private val context: Context){
    private lateinit var alarmManager: AlarmManager
    private lateinit var pendingIntent: PendingIntent

    fun createAlarm(start: Long) {
        alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, WaterAlarmReceiver::class.java)

        pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_IMMUTABLE)

        alarmManager.set(
            AlarmManager.RTC_WAKEUP,
            start,
            pendingIntent
        )
        val sdf = SimpleDateFormat("dd.MM.yyyy, HH:mm", Locale.getDefault())
        val date = sdf.format(Date(start))
        Log.i("alarm", "alarm set to $date")
    }

    fun cancelAlarm(){
        alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, WaterAlarmReceiver::class.java)
        pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_IMMUTABLE)
        alarmManager.cancel(pendingIntent)
    }
    fun isAlarmSet(): Boolean{
        val intent = Intent(context, WaterAlarmReceiver::class.java)
        Log.i("is set", "${PendingIntent.getBroadcast(context, 0, intent,PendingIntent.FLAG_IMMUTABLE) != null}")
        return PendingIntent.getBroadcast(context, 0, intent,PendingIntent.FLAG_IMMUTABLE) != null
    }

}