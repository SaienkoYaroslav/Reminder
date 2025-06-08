package com.behappy.reminder.ui.components

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.behappy.reminder.R
import com.behappy.reminder.ui.theme.Primary

@Composable
fun FloatingActionButtonAddReminder(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    FloatingActionButton(
        modifier = modifier
            .size(80.dp),
        onClick = onClick,
        containerColor = Primary,
        contentColor = Color.White,
        shape = CircleShape,
        elevation = FloatingActionButtonDefaults.elevation(defaultElevation = 8.dp)
    ) {
        Icon(
            modifier = Modifier.size(50.dp),
            imageVector = Icons.Default.Add,
            contentDescription = stringResource(R.string.go_to_adding_reminder_screen)
        )
    }

}