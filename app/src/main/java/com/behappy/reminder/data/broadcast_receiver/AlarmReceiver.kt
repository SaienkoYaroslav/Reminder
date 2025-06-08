package com.behappy.reminder.data.broadcast_receiver

import android.content.Context
import android.content.Intent
import com.behappy.reminder.data.mapping.ReminderAlarmHandler

/* ------------------------------------------------------
 * 6. BroadcastReceiver that delegates to use‑case (platform layer)
 * ---------------------------------------------------- */

class AlarmReceiver : android.content.BroadcastReceiver() {
    override fun onReceive(ctx: Context, intent: Intent) {
        val id = intent.getLongExtra(EXTRA_ID, -1)
        if (id == -1L) return
        // Delegation will be wired via WorkManager / Hilt injection to CompleteReminderUseCase & co.
        ReminderAlarmHandler.handleAlarm(ctx, id)
    }

    companion object {
        const val ACTION_ALARM = "com.behappy.reminder.ALARM"
        const val EXTRA_ID = "extra_id"
    }
}