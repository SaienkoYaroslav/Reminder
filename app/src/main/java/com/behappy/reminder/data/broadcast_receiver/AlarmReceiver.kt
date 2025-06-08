package com.behappy.reminder.data.broadcast_receiver

import android.Manifest
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.annotation.RequiresPermission
import androidx.core.app.NotificationManagerCompat
import com.behappy.reminder.data.mapping.ReminderAlarmHandler
import dagger.hilt.android.AndroidEntryPoint

/* ------------------------------------------------------
 * 6. BroadcastReceiver that delegates to use‑case (platform layer)
 * ---------------------------------------------------- */

@AndroidEntryPoint
class AlarmReceiver : BroadcastReceiver() {

    @RequiresPermission(Manifest.permission.POST_NOTIFICATIONS)
    override fun onReceive(ctx: Context, intent: Intent) {
        val id = intent.getLongExtra(EXTRA_ID, -1L)
        if (id == -1L) return

        val notif = NotificationBuilder.build(ctx, id)
        NotificationManagerCompat.from(ctx).notify(id.toInt(), notif)
    }

    companion object {
        const val ACTION_ALARM = "com.behappy.reminder.action.ALARM"
        const val EXTRA_ID = "extra_id"
    }
}