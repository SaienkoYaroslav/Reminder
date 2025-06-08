package com.behappy.reminder.data.repo_impl

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import com.behappy.reminder.data.broadcast_receiver.AlarmReceiver
import com.behappy.reminder.data.mapping.toEpochMilli
import com.behappy.reminder.domain.business_model.Reminder
import com.behappy.reminder.domain.contracts.AlarmScheduler
import java.time.LocalDateTime
import javax.inject.Inject

/* ------------------------------------------------------
 * 5. AlarmScheduler implementation (exact + idle‑mode)
 * ---------------------------------------------------- */

class AlarmSchedulerImpl @Inject constructor(
    private val context: Context,
) : AlarmScheduler {

    private val alarmManager: AlarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

    override suspend fun schedule(reminder: Reminder) {
        val next = computeNext(reminder) ?: return
        val pi = buildPendingIntent(reminder.id ?: return)
        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            next.toEpochMilli(),
            pi
        )
    }

    override suspend fun cancel(reminderId: Long) {
        alarmManager.cancel(buildPendingIntent(reminderId))
    }

    /* -------------------------------------------------- */

    private fun computeNext(reminder: Reminder): LocalDateTime? {
        val now = LocalDateTime.now()
        return when {
            reminder.snooze.isSnoozed && reminder.snooze.until != null -> reminder.snooze.until
            reminder.repeat != null -> reminder.repeat.nextAfter(reminder.trigger.nextOccurrenceAfter(now) ?: now)
            else -> reminder.trigger.nextOccurrenceAfter(now)
        }
    }

    private fun buildPendingIntent(id: Long): PendingIntent {
        val intent = Intent(context, AlarmReceiver::class.java).apply {
            action = AlarmReceiver.ACTION_ALARM
            putExtra(AlarmReceiver.EXTRA_ID, id)
        }
        val flags = PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        return PendingIntent.getBroadcast(context, id.toInt(), intent, flags)
    }
}