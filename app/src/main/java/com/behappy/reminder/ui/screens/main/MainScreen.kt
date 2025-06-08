package com.behappy.reminder.ui.screens.main

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.behappy.reminder.nav.LocalNavController

@Composable
fun MainScreen() {

    val navHostController = LocalNavController.current
    val viewModel = hiltViewModel<MainVM>()

    MainContent()

}

@Composable
fun MainContent() {

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {

        Text(
            modifier = Modifier.align(Alignment.CenterHorizontally).padding(top = 50.dp),
            text = "MainContent",
            color = Color.White
        )

    }

}

@Composable
fun LoadingView() {

}

@Composable
fun ErrorView() {

}