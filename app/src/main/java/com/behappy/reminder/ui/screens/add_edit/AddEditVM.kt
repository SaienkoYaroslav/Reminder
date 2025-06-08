package com.behappy.reminder.ui.screens.add_edit

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.behappy.reminder.di.ReminderUseCases
import com.behappy.reminder.domain.business_model.Reminder
import com.behappy.reminder.domain.business_model.TimeTrigger
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.temporal.ChronoUnit
import javax.inject.Inject

/* ------------------------------------------------------
 * 2. ViewModel
 * ---------------------------------------------------- */

@HiltViewModel
class AddEditVM @Inject constructor(
    private val useCases: ReminderUseCases,
) : ViewModel() {

    private var editingId: Long? = null  // null ⇒ create mode

    private val _uiState = MutableStateFlow(AddEditUiState())
    val uiState: StateFlow<AddEditUiState> = _uiState

    /** One‑shot init from NavGraph (id may be null). */
    fun init(id: Long?) {
        if (editingId != null) return   // already initialised
        editingId = id
        if (id == null) return          // create mode – no prefill
        viewModelScope.launch {
            val reminder = useCases.observe().first().firstOrNull { it.id == id }
            reminder?.let {
                val dt = (it.trigger as TimeTrigger).dateTime
                _uiState.update { st ->
                    st.copy(
                        title = it.title,
                        date  = dt.toLocalDate(),
                        time  = dt.toLocalTime(),
                    )
                }
            }
        }
    }

    /* --- state updates from UI --- */
    fun onTitleChange(newValue: String) = _uiState.update { it.copy(title = newValue, error = null) }
    fun onDateChange(date: LocalDate)   = _uiState.update { it.copy(date = date) }
    fun onTimeChange(time: LocalTime)   = _uiState.update { it.copy(time = time) }

    /* --- save action --- */
    fun onSave(onDone: () -> Unit) {
        viewModelScope.launch {
            val state = _uiState.value
            if (state.title.isBlank()) {
                _uiState.update { it.copy(error = "Title cannot be empty") }
                return@launch
            }
            _uiState.update { it.copy(isSaving = true) }
            val dateTime = LocalDateTime.of(state.date, state.time)
            val reminder = Reminder(
                id = editingId,
                title = state.title,
                trigger = TimeTrigger(dateTime),
                repeat = null,
            )
            if (editingId == null) {
                useCases.create(reminder)
            } else {
                useCases.update(reminder)
            }
            onDone()
        }
    }
}


/* ------------------------------------------------------
 * 1. UI‑state
 * ---------------------------------------------------- */

data class AddEditUiState(
    val title: String = "",
    val date: LocalDate = LocalDate.now(),
    val time: LocalTime = LocalTime.now().plusMinutes(1).truncatedTo(ChronoUnit.MINUTES),
    val isSaving: Boolean = false,
    val error: String? = null,
)