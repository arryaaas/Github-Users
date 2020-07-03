package com.arya.apigithubusers.reminder

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.arya.apigithubusers.R
import com.arya.apigithubusers.activity.MainActivity

class AlarmReceiver: BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        Log.d("@RECEIVE", "Notifications Succes")
        dailyReminder(context)
    }

    private fun dailyReminder(context: Context) {
        val intent = Intent(context, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(context, 0, intent, 0)

        val builder = NotificationCompat.Builder(context, "daily_reminder")
            .setContentIntent(pendingIntent)
            .setSmallIcon(R.drawable.ic_notifications_active)
            .setContentTitle("Reminder")
            .setContentText("Let's find popular user on Github!")
            .setAutoCancel(true)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        val path = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        builder.setSound(path)

        val notificationManager = NotificationManagerCompat.from(context)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "daily_channel"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel("daily_reminder", name, importance)

            val notificationM = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationM.createNotificationChannel(channel)
        }

        notificationManager.notify(1, builder.build())
    }
}