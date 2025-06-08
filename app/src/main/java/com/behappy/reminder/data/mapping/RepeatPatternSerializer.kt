package com.behappy.reminder.data.mapping

import android.content.Context
import com.behappy.reminder.domain.business_model.RepeatPattern
import java.time.DayOfWeek

/* ------------------------------------------------------
 * 7. Helper for (de)serializing RepeatPattern – very light
 * ---------------------------------------------------- */

object RepeatPatternSerializer {
    // Extremely simple string format: DAILY, WEEKLY:1,2,3, MONTHLY:15, CUSTOM:120

    fun serialize(p: RepeatPattern): String = when (p) {
        RepeatPattern.Once          -> "ONCE"
        RepeatPattern.Daily         -> "DAILY"
        is RepeatPattern.Weekly     -> "WEEKLY:${p.days.joinToString()}"
        is RepeatPattern.Monthly    -> "MONTHLY:${p.dayOfMonth}"
        is RepeatPattern.Custom     -> "CUSTOM:${p.intervalMinutes}"
    }

    fun deserialize(raw: String): RepeatPattern = when {
        raw == "ONCE"      -> RepeatPattern.Once
        raw == "DAILY"     -> RepeatPattern.Daily
        raw.startsWith("WEEKLY") -> {
            val parts = raw.substringAfter(":").split(',').map { DayOfWeek.valueOf(it) }.toSet()
            RepeatPattern.Weekly(parts)
        }
        raw.startsWith("MONTHLY") -> {
            val day = raw.substringAfter(":").toInt()
            RepeatPattern.Monthly(day)
        }
        raw.startsWith("CUSTOM")  -> {
            val min = raw.substringAfter(":").toLong()
            RepeatPattern.Custom(min)
        }
        else -> RepeatPattern.Once
    }
}

/* ------------------------------------------------------
 * 8. Platform‑level handler stub (to be wired via DI)
 * ---------------------------------------------------- */

object ReminderAlarmHandler {
    fun handleAlarm(ctx: Context, id: Long) {
        // TODO: Inject your use‑cases via ServiceLocator / EntryPoint.
        // 1. Fetch reminder from repository
        // 2. Show full‑screen notification with actions.
        // 3. If repeat → reschedule next occurrence.
    }
}