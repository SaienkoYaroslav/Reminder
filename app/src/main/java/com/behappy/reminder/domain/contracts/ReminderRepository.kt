package com.behappy.reminder.domain.contracts

import com.behappy.reminder.domain.business_model.Reminder
import kotlinx.coroutines.flow.Flow

interface ReminderRepository {
    fun remindersFlow(): Flow<List<Reminder>>
    suspend fun get(id: Long): Reminder?
    suspend fun save(reminder: Reminder): Long   // returns generated id on insert
    suspend fun delete(id: Long)
}