package com.behappy.reminder.domain.business_model

import java.time.LocalDateTime

/** When should the reminder fire? */
sealed interface Trigger {
    fun nextOccurrenceAfter(now: LocalDateTime): LocalDateTime?
}