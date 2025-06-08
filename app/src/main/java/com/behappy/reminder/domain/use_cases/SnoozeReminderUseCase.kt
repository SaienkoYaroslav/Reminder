package com.behappy.reminder.domain.use_cases

import com.behappy.reminder.domain.business_model.SnoozeState
import com.behappy.reminder.domain.contracts.AlarmScheduler
import com.behappy.reminder.domain.contracts.ReminderRepository
import java.time.LocalDateTime

class SnoozeReminderUseCase(
    private val repo: ReminderRepository,
    private val scheduler: AlarmScheduler,
    private val snoozeMinutes: Long = 10,
) {
    suspend operator fun invoke(id: Long) {
        val reminder = repo.get(id) ?: return
        val snoozedUntil = LocalDateTime.now().plusMinutes(snoozeMinutes)
        val updated = reminder.copy(
            snooze = SnoozeState(true, snoozedUntil),
            updatedAt = LocalDateTime.now()
        )
        repo.save(updated)
        scheduler.schedule(updated)
    }
}