package com.behappy.reminder.data.repo_impl

import com.behappy.reminder.data.db.dao.ReminderDao
import com.behappy.reminder.data.mapping.toDomain
import com.behappy.reminder.data.mapping.toEntity
import com.behappy.reminder.domain.business_model.Reminder
import com.behappy.reminder.domain.contracts.ReminderRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/* ------------------------------------------------------
 * 4. Repository implementation
 * ---------------------------------------------------- */

class ReminderRepositoryImpl @Inject constructor(
    private val dao: ReminderDao,
) : ReminderRepository {

    override fun remindersFlow(): Flow<List<Reminder>> = dao.observeAll().map { list ->
        list.map { it.toDomain() }
    }

    override suspend fun get(id: Long): Reminder? = dao.get(id)?.toDomain()

    override suspend fun save(reminder: Reminder): Long {
        val entity = reminder.toEntity()
        val id = dao.insert(entity)
        return if (reminder.id == null) id else reminder.id
    }

    override suspend fun delete(id: Long) {
        dao.get(id)?.let { dao.delete(it) }
    }
}