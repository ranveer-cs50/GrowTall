package com.example.util

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.MainActivity

object NotificationHelper {

    private const val CHANNEL_ID = "growth_reminders_channel"
    private const val CHANNEL_NAME = "Puberty Growth & Height Reminders"

    fun createNotificationChannel(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val descriptionText = "Notifications for pre-bedtime protein and optimal HGH sleep for height growth."
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, CHANNEL_NAME, importance).apply {
                description = descriptionText
                enableVibration(true)
            }
            val notificationManager: NotificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    fun sendGrowthReminderNotification(
        context: Context,
        title: String,
        message: String,
        notificationId: Int = 1001
    ) {
        val intent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent = PendingIntent.getActivity(
            context,
            0,
            intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setContentTitle(title)
            .setContentText(message)
            .setStyle(NotificationCompat.BigTextStyle().bigText(message))
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)

        try {
            with(NotificationManagerCompat.from(context)) {
                notify(notificationId, builder.build())
            }
        } catch (e: SecurityException) {
            // Handled when permission is not yet granted
        }
    }

    val PRESET_REMINDERS = listOf(
        Pair("🌙 Nocturnal HGH Casein Reminder", "Time for your pre-bedtime protein! A glass of milk or Greek yogurt provides slow-release amino acids for peak midnight growth hormone release."),
        Pair("⚡ Afternoon Growth Spurt Snack", "Your body is actively remodeling bone tissue. Grab an egg, edamame, or nuts for 15-20g of bone-lengthening protein!"),
        Pair("🛌 Deep Sleep Window Alert", "80% of puberty height growth happens in Stage 3 & 4 deep sleep. Wind down screen time to maximize growth plate expansion tonight!"),
        Pair("💧 Spinal Hydration Check", "Spinal discs rehydrate and decompress when hydrated! Drink a fresh glass of water to support your posture and spine.")
    )
}
