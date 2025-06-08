package com.behappy.reminder.domain.business_model

import java.time.LocalDateTime

/** Main aggregate */
data class Reminder(
    val id: Long? = null,                    // null until inserted in DB
    val title: String,
    val trigger: Trigger,
    val repeat: RepeatPattern? = null,
    val status: Status = Status.ACTIVE,
    val snooze: SnoozeState = SnoozeState(),
    val createdAt: LocalDateTime = LocalDateTime.now(),
    val updatedAt: LocalDateTime = LocalDateTime.now(),
)