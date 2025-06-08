package com.behappy.reminder.domain.use_cases

import com.behappy.reminder.domain.contracts.AlarmScheduler
import com.behappy.reminder.domain.contracts.ReminderRepository

class DeleteReminderUseCase(
    private val repo: ReminderRepository,
    private val scheduler: AlarmScheduler,
) {
    suspend operator fun invoke(id: Long) {
        repo.delete(id)
        scheduler.cancel(id)
    }
}