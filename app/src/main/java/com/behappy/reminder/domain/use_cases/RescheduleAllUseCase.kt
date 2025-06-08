package com.behappy.reminder.domain.use_cases

import com.behappy.reminder.domain.business_model.Status
import com.behappy.reminder.domain.contracts.AlarmScheduler
import com.behappy.reminder.domain.contracts.ReminderRepository

/** Re‑arm all ACTIVE reminders – call after reboot. */
class RescheduleAllUseCase(
    private val repo: ReminderRepository,
    private val scheduler: AlarmScheduler,
) {
    suspend operator fun invoke() {
        repo.remindersFlow()
            .collect { list ->
                list.filter { it.status == Status.ACTIVE }
                    .forEach { scheduler.schedule(it) }
            }
    }
}