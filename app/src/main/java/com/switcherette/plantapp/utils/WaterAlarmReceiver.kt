package com.switcherette.plantapp.utils

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.navigation.NavDeepLinkBuilder
import com.switcherette.plantapp.R
import com.switcherette.plantapp.data.repositories.WaterRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class WaterAlarmReceiver : BroadcastReceiver(), KoinComponent {

    private val CHANNEL_ID = "channel_water"
    private val notificationId = "water".hashCode()
    private val waterAlarm: WaterAlarm by inject()
    private val waterRepository: WaterRepository by inject()

    override fun onReceive(context: Context?, intent: Intent?) {
        intent!!.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        val pendingIntent = NavDeepLinkBuilder(context!!)
            .setGraph(R.navigation.nav_graph)
            .setDestination(R.id.calendarFragment)
            .createPendingIntent()

        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_flower)
            .setContentTitle("Water notification")
            .setContentText("It's time to water your plants!")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setDefaults(NotificationCompat.DEFAULT_ALL)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
//            .setStyle(NotificationCompat.BigTextStyle().bigText("")
        with(NotificationManagerCompat.from(context)) {
            notify(notificationId, builder.build())

        }
        setNewAlarm()
    }

    private fun setNewAlarm() {
        CoroutineScope(Dispatchers.IO).launch {
            val currentDate = waterRepository.getFirstWaterEventByDate()!!.repeatStart
            waterRepository.getWaterEventByDate(currentDate)
                .map {
                    Log.i("yeet", "Result: ${it.repeatStart + it.repeatInterval}"
                )
                    it.copy(repeatStart = it.repeatStart + it.repeatInterval) }
                .let { waterRepository.updateDates(it) }
            val newDate = waterRepository.getFirstWaterEventByDate()!!.repeatStart
            waterAlarm.createAlarm(newDate)
            Log.i("yeet", "")
        }


    }
}