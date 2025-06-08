package com.behappy.reminder.nav

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.behappy.reminder.ui.screens.main.MainScreen

@Composable
fun NavGraph(
) {
    val navHostController = LocalNavController.current
    NavHost(
        navController = navHostController,
        startDestination = RoutMainScreen
    ) {

        composable<RoutMainScreen> { MainScreen() }

    }
}