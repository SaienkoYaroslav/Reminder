package com.behappy.reminder.domain.use_cases

import com.behappy.reminder.domain.business_model.Reminder
import com.behappy.reminder.domain.contracts.AlarmScheduler
import com.behappy.reminder.domain.contracts.ReminderRepository
import java.time.LocalDateTime

class UpdateReminderUseCase(
    private val repo: ReminderRepository,
    private val scheduler: AlarmScheduler,
) {
    suspend operator fun invoke(reminder: Reminder) {
        requireNotNull(reminder.id) { "Reminder id is null" }
        repo.save(reminder.copy(updatedAt = LocalDateTime.now()))
        scheduler.schedule(reminder) // reschedule
    }
}