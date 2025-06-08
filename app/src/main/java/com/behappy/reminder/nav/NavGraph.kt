package com.behappy.reminder.nav

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.toRoute
import com.behappy.reminder.ui.screens.add_edit.AddEditScreen
import com.behappy.reminder.ui.screens.add_edit.AddEditVM
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

        composable<RoutAddEditScreen> { route ->
            val vm: AddEditVM = hiltViewModel()
            val args = route.toRoute<RoutAddEditScreen>()
            AddEditScreen(
                navController = navHostController,
                viewModel = vm,
                editingId = args.id,
            )
        }

    }
}