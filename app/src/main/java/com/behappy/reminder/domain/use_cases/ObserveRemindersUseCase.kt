package com.behappy.reminder.domain.use_cases

import com.behappy.reminder.domain.business_model.Reminder
import com.behappy.reminder.domain.contracts.ReminderRepository
import kotlinx.coroutines.flow.Flow

class ObserveRemindersUseCase(private val repo: ReminderRepository) {
    operator fun invoke(): Flow<List<Reminder>> = repo.remindersFlow()
}