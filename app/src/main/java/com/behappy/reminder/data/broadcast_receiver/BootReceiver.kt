package com.behappy.reminder.data.broadcast_receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.behappy.reminder.di.ReminderEP
import dagger.hilt.android.EntryPointAccessors
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class BootReceiver : BroadcastReceiver() {

    override fun onReceive(ctx: Context, intent: Intent) {
        if (intent.action != Intent.ACTION_BOOT_COMPLETED) return

        // 1️⃣ одержуємо PendingResult, щоб не «вбили» процес
        val result = goAsync()

        // 2️⃣ дістаємо UseCases через EntryPoint
        val ep = EntryPointAccessors.fromApplication(ctx, ReminderEP::class.java)

        // 3️⃣ запускаємо корутину й обов’язково завершуємо PendingResult
        CoroutineScope(Dispatchers.Default).launch {
            ep.useCases().rescheduleAll()
            result.finish()          // повідомляємо ОС, що роботу завершено
        }
    }
}
