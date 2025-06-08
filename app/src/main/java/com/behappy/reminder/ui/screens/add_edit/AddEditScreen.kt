package com.behappy.reminder.ui.screens.add_edit

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.behappy.reminder.R
import com.behappy.reminder.ui.components.TopAppBar
import com.behappy.reminder.ui.theme.PrimaryContainer
import com.behappy.reminder.ui.theme.Surface
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter

@Composable
fun AddEditScreen(
    navController: NavHostController,
    viewModel: AddEditVM,
    editingId: Long?,                // ← передаємо з NavGraph
) {

    var isBackBtnEnabled by remember { mutableStateOf(true) }

    LaunchedEffect(editingId) { viewModel.init(editingId) }

    val context = LocalContext.current
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    var showDatePicker by remember { mutableStateOf(false) }
    var showTimePicker by remember { mutableStateOf(false) }

    // Classic platform dialogs


    Scaffold(
        contentWindowInsets = WindowInsets.statusBars.only(
            WindowInsetsSides.Horizontal
        ),
        topBar = {
            TopAppBar(
                text = stringResource(if (editingId == null) R.string.add_reminder else R.string.edit_reminder),
                isBackBtnNeed = true,
                isButtonEnabled = isBackBtnEnabled,
                onClickBack = {
                    isBackBtnEnabled = false
                    navController.popBackStack()
                }
            )
        },
        containerColor = Surface,
        floatingActionButton = {
            ExtendedFloatingActionButton(
                text = { Text(stringResource(R.string.save)) },
                icon = { Icon(Icons.Default.Check, null) },
                onClick = { viewModel.onSave { navController.popBackStack() } },
                expanded = true,
//                enabled = state.title.isNotBlank() && !state.isSaving
            )
        },
        modifier = Modifier
            .fillMaxSize()
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(24.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                // Title
                OutlinedTextField(
                    value = state.title,
                    onValueChange = viewModel::onTitleChange,
                    label = { Text(stringResource(R.string.title)) },
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = PrimaryContainer,
                        focusedLabelColor = PrimaryContainer,
                        focusedTextColor = Color.White
                    ),
                    modifier = Modifier.fillMaxWidth(),
                )

                // Date selector with transparent click‑layer
                OutlinedTextField(
                    value = state.date.format(DateTimeFormatter.ofPattern("dd MMM yyyy")),
                    onValueChange = {},
                    label = { Text(stringResource(R.string.date)) },
                    readOnly = true,
                    enabled = false,
                    colors = OutlinedTextFieldDefaults.colors(
                        disabledBorderColor = PrimaryContainer,
                        disabledLabelColor = PrimaryContainer,
                        disabledTextColor = Color.White
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { showDatePicker = true },
                )

                // Time selector with transparent click‑layer

                OutlinedTextField(
                    value = state.time.format(DateTimeFormatter.ofPattern("HH:mm")),
                    onValueChange = {},
                    label = { Text(stringResource(R.string.time)) },
                    readOnly = true,
                    enabled = false,
                    colors = OutlinedTextFieldDefaults.colors(
                        disabledBorderColor = PrimaryContainer,
                        disabledLabelColor = PrimaryContainer,
                        disabledTextColor = Color.White
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { showTimePicker = true },
                )


                state.error?.let {
                    Text(it, color = MaterialTheme.colorScheme.error)
                }

                /* ---------- DatePicker ---------- */
                if (showDatePicker) {
                    val dlg = DatePickerDialog(
                        context,
                        { _, y, m, d ->
                            viewModel.onDateChange(LocalDate.of(y, m + 1, d))
                            showDatePicker = false
                        },
                        state.date.year,
                        state.date.monthValue - 1,
                        state.date.dayOfMonth
                    )
                    dlg.setOnDismissListener { showDatePicker = false }
                    dlg.show()
                }

                /* ---------- TimePicker ---------- */
                if (showTimePicker) {
                    val dlg = TimePickerDialog(
                        context,
                        { _, h, min ->
                            viewModel.onTimeChange(LocalTime.of(h, min))
                            showTimePicker = false
                        },
                        state.time.hour,
                        state.time.minute,
                        true
                    )
                    dlg.setOnDismissListener { showTimePicker = false }
                    dlg.show()
                }
            }
        }
    }
}