package com.behappy.reminder.data.repo_impl

import com.behappy.reminder.domain.contracts.AlarmScheduler
import com.behappy.reminder.domain.contracts.ReminderRepository
import javax.inject.Inject

class ReminderAlarmRescheduler @Inject constructor(
    private val repo: ReminderRepository,
    private val scheduler: AlarmScheduler
) {
    suspend fun reschedule(id: Long) {
        repo.get(id)?.let { scheduler.schedule(it) }
    }
}