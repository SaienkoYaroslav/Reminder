package com.behappy.reminder.ui.screens.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.behappy.reminder.di.ReminderUseCases
import com.behappy.reminder.domain.business_model.Reminder
import com.behappy.reminder.domain.business_model.Status
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainVM @Inject constructor(
    private val useCases: ReminderUseCases
): ViewModel() {

    val uiState: StateFlow<MainUiState> = useCases.observe()
        .map { list ->
            MainUiState(
                active = list.filter { it.status == Status.ACTIVE },
                completed = list.filter { it.status == Status.COMPLETED },
            )
        }
        .stateIn(viewModelScope, SharingStarted.Eagerly, MainUiState())

    fun onDelete(id: Long) = viewModelScope.launch { useCases.delete(id) }
}


/* ------------------------------------------------------
 * 1. UI-state (active + completed)
 * ---------------------------------------------------- */

data class MainUiState(
    val active: List<Reminder> = emptyList(),
    val completed: List<Reminder> = emptyList(),
)
