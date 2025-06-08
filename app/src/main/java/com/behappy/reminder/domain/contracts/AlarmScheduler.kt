package com.behappy.reminder.domain.contracts

import com.behappy.reminder.domain.business_model.Reminder

/** Scheduler is responsible only for OS‑level planning/cancellation. */
interface AlarmScheduler {
    suspend fun schedule(reminder: Reminder)
    suspend fun cancel(reminderId: Long)
}