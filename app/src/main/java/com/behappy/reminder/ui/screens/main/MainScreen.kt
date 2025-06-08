package com.behappy.reminder.ui.screens.main

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.behappy.reminder.R
import com.behappy.reminder.domain.business_model.Reminder
import com.behappy.reminder.domain.business_model.TimeTrigger
import com.behappy.reminder.nav.LocalNavController
import com.behappy.reminder.nav.RoutAddEditScreen
import com.behappy.reminder.ui.components.BaseAlertDialog
import com.behappy.reminder.ui.components.FloatingActionButtonAddReminder
import com.behappy.reminder.ui.components.TopAppBar
import com.behappy.reminder.ui.theme.Surface
import kotlinx.coroutines.launch
import java.time.format.DateTimeFormatter

@Composable
fun MainScreen() {

    val navHostController = LocalNavController.current
    val viewModel = hiltViewModel<MainVM>()
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    MainContent(
        state = state,
        onGoAddEditScreen = { navHostController.navigate(RoutAddEditScreen()) },
        onClickItem = { navHostController.navigate(RoutAddEditScreen(id = it)) },
        onDeleteItem = viewModel::onDelete
    )

}

@Composable
fun MainContent(
    state: MainUiState,
    onGoAddEditScreen: () -> Unit,
    onDeleteItem: (Long) -> Unit,
    onClickItem: (Long) -> Unit,
) {

    val systemBottomPudding = WindowInsets.systemBars.asPaddingValues().calculateBottomPadding()

    val pagerState = rememberPagerState(initialPage = 0, pageCount = { 2 })
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        contentWindowInsets = WindowInsets.statusBars.only(
            WindowInsetsSides.Horizontal
        ),
        topBar = {
            TopAppBar(
                text = stringResource(R.string.reminder)
            )
        },
        containerColor = Surface,
        floatingActionButton = {
            FloatingActionButtonAddReminder(
                modifier = Modifier.padding(bottom = systemBottomPudding + 50.dp)
            ) {
                onGoAddEditScreen()
            }

        },
        floatingActionButtonPosition = FabPosition.Center,
        modifier = Modifier
            .fillMaxSize()
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            /* ---- Tabs ---- */
            TabRow(selectedTabIndex = pagerState.currentPage) {
                Tab(
                    selected = pagerState.currentPage == 0,
                    onClick = { coroutineScope.launch { pagerState.animateScrollToPage(0) } },
                    text = { Text(stringResource(R.string.active)) },
                    icon = { Icon(Icons.Default.DateRange, null) }
                )
                Tab(
                    selected = pagerState.currentPage == 1,
                    onClick = { coroutineScope.launch { pagerState.animateScrollToPage(1) } },
                    text = { Text(stringResource(R.string.completed)) },
                    icon = { Icon(Icons.Default.CheckCircle, null) }
                )
            }
            /* ---- Pager ---- */
            HorizontalPager(state = pagerState, modifier = Modifier.weight(1f)) { page ->
                when (page) {
                    0 -> ReminderList(
                        state.active,
                        emptyText = stringResource(R.string.no_active),
                        onDeleteItem = {onDeleteItem(it)}
                    ) {
                        onClickItem(it)
                    }

                    1 -> ReminderList(
                        state.completed,
                        emptyText = stringResource(R.string.no_completed),
                        onDeleteItem = {onDeleteItem(it)}
                    ) {
                        onClickItem(it)
                    }
                }
            }

        }
    }


}

@Composable
fun LoadingView() {

}

@Composable
fun ErrorView() {

}

@Composable
fun ReminderList(list: List<Reminder>, emptyText: String, onDeleteItem: (Long) -> Unit, onClickItem: (Long) -> Unit) {
    if (list.isEmpty()) {
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(emptyText, color = Color.Gray)
        }
    } else {
        LazyColumn(
            Modifier.fillMaxSize(),
            contentPadding = PaddingValues(vertical = 12.dp)
        ) {
            items(list, key = { it.id ?: 0 }) { reminder ->
                ReminderItem(
                    reminder,
                    onDelete = {
                        onDeleteItem(it)
                    }
                ) {
                    onClickItem(it)
                }
            }
        }
    }
}

@Composable
fun ReminderItem(
    reminder: Reminder,
    onDelete: (Long) -> Unit,
    onClick: (Long) -> Unit,

) {

    var expanded by remember { mutableStateOf(false) }
    var openDeleteDialog by remember { mutableStateOf(false) }

    if (openDeleteDialog) {
        BaseAlertDialog(
            onDismissRequest = {
                openDeleteDialog = false
            },
            confirmButtonClick = {
                openDeleteDialog = false
                onDelete(reminder.id!!)
            }
        )
    }
    
    Card(
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 4.dp)
            .fillMaxWidth(),
        onClick = { onClick(reminder.id!!) }
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                Modifier.padding(16.dp)
            ) {
                Text(reminder.title, style = MaterialTheme.typography.titleMedium)
                val dt = (reminder.trigger as? TimeTrigger)?.dateTime
                dt?.let {
                    Text(
                        it.format(DateTimeFormatter.ofPattern("dd MMM yyyy  HH:mm")),
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
            IconButton(
                modifier = Modifier
                    .padding(top = 8.dp, end = 4.dp)
                    .size(25.dp),
                onClick = {
                    expanded = true
                }
            ) {
                Icon(
                    modifier = Modifier.size(20.dp),
                    imageVector = Icons.Default.MoreVert,
                    contentDescription = null,
                    tint = Color.White
                )
                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                    containerColor = Color.White
                ) {
                    DropdownMenuItem(
                        text = {
                            Text(
                                text = "Delete",
                                color = Color.Red
                            )
                        },
                        trailingIcon = {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = null,
                                tint = Color.Red
                            )
                        },
                        onClick = {
                            expanded = false
                            openDeleteDialog = true
                        }
                    )
                }
            }
        }

    }
}