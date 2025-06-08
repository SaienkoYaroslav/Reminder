package com.behappy.reminder.ui.screens

import android.Manifest
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.systemBars
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.behappy.reminder.nav.LocalNavController
import com.behappy.reminder.nav.NavGraph
import com.behappy.reminder.nav.RoutAddEditScreen
import com.behappy.reminder.nav.RoutMainScreen
import com.behappy.reminder.nav.RoutScreen
import com.behappy.reminder.nav.routeClass
import com.behappy.reminder.ui.components.FloatingActionButtonAddReminder
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
                val launcher = rememberLauncherForActivityResult(
                    ActivityResultContracts.RequestPermission()
                ){

                }
                LaunchedEffect(Unit) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                        launcher.launch(Manifest.permission.POST_NOTIFICATIONS)
                    }
                }

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