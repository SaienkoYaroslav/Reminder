package com.behappy.reminder.data.mapping

import com.behappy.reminder.data.db.entety.ReminderEntity
import com.behappy.reminder.domain.business_model.Reminder
import com.behappy.reminder.domain.business_model.SnoozeState
import com.behappy.reminder.domain.business_model.Status
import com.behappy.reminder.domain.business_model.TimeTrigger
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId

/* ------------------------------------------------------
 * 3. Mapping helpers
 * ---------------------------------------------------- */

fun ReminderEntity.toDomain(): Reminder = Reminder(
    id = id,
    title = title,
    trigger = TimeTrigger(triggerTime.toLocalDateTime()),
    repeat = repeatPattern?.let { RepeatPatternSerializer.deserialize(it) },
    status = Status.valueOf(status),
    snooze = SnoozeState(
        isSnoozed = snoozedUntil != null,
        until = snoozedUntil?.toLocalDateTime(),
    ),
    createdAt = createdAt.toLocalDateTime(),
    updatedAt = updatedAt.toLocalDateTime(),
)

fun Reminder.toEntity(): ReminderEntity = ReminderEntity(
    id = id ?: 0,
    title = title,
    triggerTime = (trigger as TimeTrigger).dateTime.toEpochMilli(),
    repeatPattern = repeat?.let { RepeatPatternSerializer.serialize(it) },
    status = status.name,
    snoozedUntil = snooze.until?.toEpochMilli(),
    createdAt = createdAt.toEpochMilli(),
    updatedAt = updatedAt.toEpochMilli(),
)

fun LocalDateTime.toEpochMilli(): Long =
    atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()

fun Long.toLocalDateTime(): LocalDateTime =
    Instant.ofEpochMilli(this).atZone(ZoneId.systemDefault()).toLocalDateTime()