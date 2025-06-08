package com.behappy.reminder.data.broadcast_receiver

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.behappy.reminder.R
import com.behappy.reminder.data.broadcast_receiver.AlarmReceiver.Companion.EXTRA_ID
import com.behappy.reminder.ui.screens.MainActivity

object NotificationBuilder {

    private const val CHANNEL_ID = "reminder_alarm"

    fun build(ctx: Context, id: Long): Notification {
        createChannelIfNeeded(ctx)

        /* ─── Дії ─── */
        val completePi = PendingIntent.getBroadcast(
            ctx, id.toInt(),
            Intent(ctx, CompleteActionReceiver::class.java).putExtra(EXTRA_ID, id),
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val snoozePi = PendingIntent.getBroadcast(
            ctx, id.toInt(),
            Intent(ctx, SnoozeActionReceiver::class.java).putExtra(EXTRA_ID, id),
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        /* ─── Повноекранний інтент (MainActivity) ─── */
        val fullScreenPi = PendingIntent.getActivity(
            ctx, id.toInt(),
            Intent(ctx, MainActivity::class.java)
                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP)
                .putExtra(EXTRA_ID, id),
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        return NotificationCompat.Builder(ctx, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_stat_name)           // створи 24×24 dp
            .setContentTitle(ctx.getString(R.string.app_name))
            .setContentText(ctx.getString(R.string.reminder_alarm_msg))
            .setCategory(NotificationCompat.CATEGORY_ALARM)
            .setPriority(NotificationCompat.PRIORITY_MAX)
            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
            .setContentIntent(fullScreenPi)
            .setFullScreenIntent(fullScreenPi, true)
            .addAction(R.drawable.ic_done, ctx.getString(R.string.complete),  completePi)
            .addAction(R.drawable.ic_snooze, ctx.getString(R.string.snooze_10m), snoozePi)
            .setAutoCancel(true)
            .build()
    }

    private fun createChannelIfNeeded(ctx: Context) {
        val nm = ctx.getSystemService(NotificationManager::class.java)
        if (nm.getNotificationChannel(CHANNEL_ID) == null) {
            val ch = NotificationChannel(
                CHANNEL_ID,
                ctx.getString(R.string.channel_reminder),
                NotificationManager.IMPORTANCE_HIGH
            ).apply { lockscreenVisibility = Notification.VISIBILITY_PUBLIC }
            nm.createNotificationChannel(ch)
        }
    }
}