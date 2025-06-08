package com.behappy.reminder.domain.business_model

import java.time.LocalDateTime

/** Temporary postponement */
data class SnoozeState(
    val isSnoozed: Boolean = false,
    val until: LocalDateTime? = null,
)