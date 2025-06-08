package com.behappy.reminder.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.behappy.reminder.data.db.dao.ReminderDao
import com.behappy.reminder.data.db.entety.ReminderEntity
import com.behappy.reminder.data.db.type_converters.Converters

@Database(
    entities = [ReminderEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class ReminderDatabase : RoomDatabase() {
    abstract fun dao(): ReminderDao
}