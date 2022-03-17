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
    private val CHANNEL_ID = "channel_id_example"
    private val notificationId = 420
    override fun onReceive(context: Context?, intent: Intent?) {
        intent!!.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        val pendingIntent = NavDeepLinkBuilder(context!!)
            .setGraph(R.navigation.nav_graph)
            .setDestination(R.id.calendarFragment)
            .createPendingIntent()

        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.plant)
            .setContentTitle("Water notification")
            .setContentText("Example description")
            .setStyle(NotificationCompat.BigTextStyle().bigText("Etiam mollis, turpis nec venenatis congue, nulla nunc laoreet neque, eget facilisis lacus tortor a mi. Nunc convallis pretium dui id pretium. Morbi varius sodales auctor."))
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setDefaults(NotificationCompat.DEFAULT_ALL)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)

        with(NotificationManagerCompat.from(context)){
            notify(notificationId, builder.build())
        }
    }
}