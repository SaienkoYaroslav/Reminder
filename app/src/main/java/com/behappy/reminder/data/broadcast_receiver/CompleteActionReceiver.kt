/* CompleteActionReceiver.kt */
package com.behappy.reminder.data.broadcast_receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationManagerCompat
import com.behappy.reminder.data.broadcast_receiver.AlarmReceiver.Companion.EXTRA_ID
import com.behappy.reminder.di.ReminderEP
import dagger.hilt.android.EntryPointAccessors
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CompleteActionReceiver : BroadcastReceiver() {
    override fun onReceive(ctx: Context, intent: Intent) {
        val id = intent.getLongExtra(EXTRA_ID, -1L)
        if (id == -1L) return

        val ep = EntryPointAccessors.fromApplication(ctx, ReminderEP::class.java)
        CoroutineScope(Dispatchers.Default).launch {
            ep.useCases().complete(id)
            with(NotificationManagerCompat.from(ctx)) {
                cancel(id.toInt())
            }
        }
    }
}
