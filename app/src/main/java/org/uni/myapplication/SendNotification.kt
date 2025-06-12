package org.uni.myapplication

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat

fun sendNotification(context: Context, title: String, message: String) {
    val channelId = "default_channel_id"
    val notificationManager =
        context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    // For Android 8.0+ create the channel
    val channel = NotificationChannel(
        channelId,
        "Default Channel",
        NotificationManager.IMPORTANCE_DEFAULT
    )
    notificationManager.createNotificationChannel(channel)

    val builder = NotificationCompat.Builder(context, channelId)
        .setSmallIcon(android.R.drawable.ic_dialog_info)  // Or your own icon
        .setContentTitle(title)
        .setContentText(message)
        .setPriority(NotificationCompat.PRIORITY_DEFAULT)

    notificationManager.notify(1, builder.build())
}
