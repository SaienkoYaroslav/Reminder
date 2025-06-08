package com.behappy.reminder.di

import android.content.Context
import androidx.room.Room
import com.behappy.reminder.data.db.ReminderDatabase
import com.behappy.reminder.data.db.dao.ReminderDao
import com.behappy.reminder.data.repo_impl.AlarmSchedulerImpl
import com.behappy.reminder.data.repo_impl.ReminderRepositoryImpl
import com.behappy.reminder.domain.contracts.AlarmScheduler
import com.behappy.reminder.domain.contracts.ReminderRepository
import com.behappy.reminder.domain.use_cases.CompleteReminderUseCase
import com.behappy.reminder.domain.use_cases.CreateReminderUseCase
import com.behappy.reminder.domain.use_cases.DeleteReminderUseCase
import com.behappy.reminder.domain.use_cases.ObserveRemindersUseCase
import com.behappy.reminder.domain.use_cases.RescheduleAllUseCase
import com.behappy.reminder.domain.use_cases.SnoozeReminderUseCase
import com.behappy.reminder.domain.use_cases.UpdateReminderUseCase
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/* ------------------------------------------------------
 * 1. Database + DAO
 * ---------------------------------------------------- */

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): ReminderDatabase =
        Room.databaseBuilder(
            context,
            ReminderDatabase::class.java,
            "reminder.db"
        )
            .fallbackToDestructiveMigration() // OK for pet‑project
            .build()

    @Provides
    fun provideDao(db: ReminderDatabase): ReminderDao = db.dao()
}

/* ------------------------------------------------------
 * 2. Repository & Scheduler bindings
 * ---------------------------------------------------- */

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindRepository(impl: ReminderRepositoryImpl): ReminderRepository

    @Binds
    @Singleton
    abstract fun bindAlarmScheduler(impl: AlarmSchedulerImpl): AlarmScheduler
}

/* ------------------------------------------------------
 * 3. Use‑cases bundle
 * ---------------------------------------------------- */

data class ReminderUseCases(
    val create: CreateReminderUseCase,
    val update: UpdateReminderUseCase,
    val delete: DeleteReminderUseCase,
    val complete: CompleteReminderUseCase,
    val snooze: SnoozeReminderUseCase,
    val observe: ObserveRemindersUseCase,
    val rescheduleAll: RescheduleAllUseCase,
)

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    @Singleton
    fun provideReminderUseCases(
        repo: ReminderRepository,
        scheduler: AlarmScheduler,
    ): ReminderUseCases = ReminderUseCases(
        create = CreateReminderUseCase(repo, scheduler),
        update = UpdateReminderUseCase(repo, scheduler),
        delete = DeleteReminderUseCase(repo, scheduler),
        complete = CompleteReminderUseCase(repo, scheduler),
        snooze = SnoozeReminderUseCase(repo, scheduler),
        observe = ObserveRemindersUseCase(repo),
        rescheduleAll = RescheduleAllUseCase(repo, scheduler),
    )
}

@EntryPoint
@InstallIn(SingletonComponent::class)
interface ReminderEP {
    fun useCases(): ReminderUseCases
}
