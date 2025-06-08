package com.behappy.reminder.data.db.entety

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "reminders")
data class ReminderEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val title: String,
    @ColumnInfo(name = "trigger_time") val triggerTime: Long,          // epoch millis (TimeTrigger only)
    val repeatPattern: String?,                                         // serialized RepeatPattern
    val status: String,                                                 // Status enum name
    @ColumnInfo(name = "snoozed_until") val snoozedUntil: Long?,       // epoch millis
    @ColumnInfo(name = "created_at") val createdAt: Long,
    @ColumnInfo(name = "updated_at") val updatedAt: Long,
)