package me.toptas.monitto.util

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.support.v4.app.NotificationCompat
import me.toptas.monitto.R

class NotificationTask(private val msg: String) {

    fun showNotification(id: Int, context: Context) {
        val notification = NotificationCompat.Builder(context, "1")
                .setContentText(msg)
                .setContentTitle(context.getString(R.string.app_name))
                .setSmallIcon(R.mipmap.ic_launcher)
                .setChannelId("1")
                .build()

        val nm = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            nm.createNotificationChannel(NotificationChannel("1", "channel", NotificationManager.IMPORTANCE_DEFAULT))
        }
        nm.notify(id, notification)

    }
}