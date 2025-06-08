package com.behappy.reminder.ui.screens

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.behappy.reminder.nav.LocalNavController
import com.behappy.reminder.nav.NavGraph
import com.behappy.reminder.nav.routeClass
import com.behappy.reminder.ui.components.TopAppBar
import com.behappy.reminder.ui.theme.ReminderTheme
import com.behappy.reminder.ui.theme.Surface
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ReminderTheme {

                val systemUiController = rememberSystemUiController()
                val navHostController = rememberNavController()
                val navBackStackEntry by navHostController.currentBackStackEntryAsState()
                val destination = navBackStackEntry.routeClass()

                SideEffect {
                    systemUiController.setSystemBarsColor(
                        color = Color.Transparent,
                        isNavigationBarContrastEnforced = false,
                        darkIcons = false
                    )
                }

                Scaffold(
                    contentWindowInsets = WindowInsets.statusBars.only(
                        WindowInsetsSides.Horizontal
                    ),
                    topBar = {
                        TopAppBar()
                    },
                    containerColor = Surface,
                    modifier = Modifier
                        .fillMaxSize()
                ) { innerPadding ->
                    Box(
                        Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                    ) {
                        CompositionLocalProvider(
                            LocalNavController provides navHostController
                        ) {
                            NavGraph()
                        }

                    }
                }
            }
        }
    }
}