package com.behappy.reminder.domain.business_model

import java.time.LocalDateTime

/** Placeholder for future location‑based trigger. */
data class GeoTrigger(
    val latitude: Double,
    val longitude: Double,
    val radiusMeters: Float,
) : Trigger {
    override fun nextOccurrenceAfter(now: LocalDateTime): LocalDateTime? = null // handled by geofencing
}