package com.switcherette.plantapp.utils

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.navigation.NavDeepLinkBuilder
import com.switcherette.plantapp.R

class WaterAlarmReceiver: BroadcastReceiver() {
    private val CHANNEL_ID = "channel_water"
    private val notificationId = "water".hashCode()
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
//            .setStyle(NotificationCompat.BigTextStyle().bigText(""))

        with(NotificationManagerCompat.from(context)){
            notify(notificationId, builder.build())
        }
    }
}