package com.behappy.reminder.domain.business_model

import java.time.DayOfWeek
import java.time.LocalDateTime

/** Optional repetition rule */
sealed interface RepeatPattern {
    fun nextAfter(previous: LocalDateTime): LocalDateTime

    /** Fire only once. */
    data object Once : RepeatPattern {
        override fun nextAfter(previous: LocalDateTime): LocalDateTime = previous
    }

    /** Every day at the same time. */
    data object Daily : RepeatPattern {
        override fun nextAfter(previous: LocalDateTime): LocalDateTime =
            previous.plusDays(1)
    }

    /** Every week on selected days (e.g. MON, WED, FRI). */
    data class Weekly(val days: Set<DayOfWeek>) : RepeatPattern {
        override fun nextAfter(previous: LocalDateTime): LocalDateTime {
            var next = previous.plusDays(1)
            while (next.dayOfWeek !in days) next = next.plusDays(1)
            return next
        }
    }

    /** Every month on a given day (1‑31). */
    data class Monthly(val dayOfMonth: Int) : RepeatPattern {
        override fun nextAfter(previous: LocalDateTime): LocalDateTime {
            var next = previous.plusMonths(1).withDayOfMonth(dayOfMonth.coerceAtMost(28))
            if (next.dayOfMonth != dayOfMonth) {
                // handle shorter months gracefully
                next = next.withDayOfMonth(next.month.length(next.toLocalDate().isLeapYear))
            }
            return next
        }
    }

    /** Arbitrary interval in minutes. */
    data class Custom(val intervalMinutes: Long) : RepeatPattern {
        override fun nextAfter(previous: LocalDateTime): LocalDateTime =
            previous.plusMinutes(intervalMinutes)
    }
}