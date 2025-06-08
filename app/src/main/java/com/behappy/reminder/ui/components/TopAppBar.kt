package com.behappy.reminder.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBar(
    modifier: Modifier = Modifier,
    isBackBtnNeed: Boolean = false,
    isButtonEnabled: Boolean = true,
    onClickBack: () -> Unit = {},
    text: String
) {

    CenterAlignedTopAppBar(
        modifier = modifier,
        title = {
            Text(
                text = text,
                style = MaterialTheme.typography.titleLarge
            )
        },
        navigationIcon = {
            if (isBackBtnNeed){
                IconButton(
                    enabled = isButtonEnabled,
                    onClick = { onClickBack() }
                ) {
                    Icon(Icons.Default.ArrowBack, contentDescription = null)
                }
            }

        }
    )

}