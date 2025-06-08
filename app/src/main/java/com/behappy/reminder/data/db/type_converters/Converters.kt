package com.behappy.reminder.data.db.type_converters

import androidx.room.TypeConverter
import com.behappy.reminder.data.mapping.RepeatPatternSerializer
import com.behappy.reminder.domain.business_model.RepeatPattern
import com.behappy.reminder.domain.business_model.Status
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId

/* ------------------------------------------------------
 * 1. TypeConverters for Room ←→ java.time / enums / sets
 * ---------------------------------------------------- */

class Converters {
    @TypeConverter
    fun toLocalDateTime(epoch: Long?): LocalDateTime? =
        epoch?.let { Instant.ofEpochMilli(it).atZone(ZoneId.systemDefault()).toLocalDateTime() }

    @TypeConverter fun fromLocalDateTime(dateTime: LocalDateTime?): Long? =
        dateTime?.atZone(ZoneId.systemDefault())?.toInstant()?.toEpochMilli()

    // RepeatPattern
    @TypeConverter fun toRepeatPattern(raw: String?): RepeatPattern? = raw?.let { RepeatPatternSerializer.deserialize(it) }
    @TypeConverter fun fromRepeatPattern(pattern: RepeatPattern?): String? = pattern?.let { RepeatPatternSerializer.serialize(it) }

    // Status
    @TypeConverter fun toStatus(s: String?): Status? = s?.let { Status.valueOf(it) }
    @TypeConverter fun fromStatus(status: Status?): String? = status?.name
}