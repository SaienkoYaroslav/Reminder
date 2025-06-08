package com.behappy.reminder.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun BaseAlertDialog(
    text: String = "Are you sure?",
    confirmText: String = "Delete",
    onDismissRequest: () -> Unit,
    confirmButtonClick: () -> Unit,
) {

    AlertDialog(
        shape = RoundedCornerShape(20.dp),
        onDismissRequest = onDismissRequest,
        title = {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = "Warning!",
                color = Color.Black,
                textAlign = TextAlign.Center
            )
        },
        text = {
            Text(
                text,
                color = Color.Black,
                textAlign = TextAlign.Center
            )
        },
        containerColor = Color.White,
        confirmButton = {
            TextButton(
                onClick = confirmButtonClick
            ) {
                Text(
                    text = confirmText,
                    color = Color.Red
                )
            }
        },
        dismissButton = {
            TextButton(
                onClick = onDismissRequest
            ) {
                Text(
                    text = "Cancel",
                    color = Color.Black
                )
            }
        }
    )
}