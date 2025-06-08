package com.behappy.reminder.domain.use_cases

import com.behappy.reminder.domain.business_model.Reminder
import com.behappy.reminder.domain.contracts.AlarmScheduler
import com.behappy.reminder.domain.contracts.ReminderRepository

class CreateReminderUseCase(
    private val repo: ReminderRepository,
    private val scheduler: AlarmScheduler,
) {
    /**
     * @return id of newly created reminder
     */
    suspend operator fun invoke(reminder: Reminder): Long {
        require(reminder.title.isNotBlank()) { "Title can't be empty" }
        val id = repo.save(reminder)
        repo.get(id)?.let { scheduler.schedule(it) }
        return id
    }
}