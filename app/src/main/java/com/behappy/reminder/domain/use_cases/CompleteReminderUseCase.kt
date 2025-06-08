package com.behappy.reminder.domain.use_cases

import com.behappy.reminder.domain.business_model.Status
import com.behappy.reminder.domain.contracts.AlarmScheduler
import com.behappy.reminder.domain.contracts.ReminderRepository
import java.time.LocalDateTime

class CompleteReminderUseCase(
    private val repo: ReminderRepository,
    private val scheduler: AlarmScheduler,
) {
    suspend operator fun invoke(id: Long) {
        val reminder = repo.get(id) ?: return
        if (reminder.status == Status.COMPLETED) return
        val updated = reminder.copy(status = Status.COMPLETED, updatedAt = LocalDateTime.now())
        repo.save(updated)
        scheduler.cancel(id)
    }
}