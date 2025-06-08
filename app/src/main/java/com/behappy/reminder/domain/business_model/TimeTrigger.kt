package com.behappy.reminder.domain.business_model

import java.time.LocalDateTime

/** Simple date‑time trigger (MVP). */
data class TimeTrigger(val dateTime: LocalDateTime) : Trigger {
    override fun nextOccurrenceAfter(now: LocalDateTime): LocalDateTime? =
        if (dateTime.isAfter(now)) dateTime else null
}