package com.cmpe451.resq.ui.views.screens
import android.content.Context
import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.cmpe451.resq.ui.theme.MyTasksColor
import com.cmpe451.resq.viewmodels.TasksViewModel
import com.cmpe451.resq.ui.views.components.TaskCard


@Composable
fun MyTasksScreen(navController: NavController, appContext: Context) {
    val viewModel: TasksViewModel = viewModel()
    val tasks by viewModel.tasks
    val scrollState = rememberScrollState()

    LaunchedEffect(key1 = true) {
        viewModel.viewMyTasks(appContext)
    }
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "My Tasks", color = MyTasksColor) },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                backgroundColor = Color.White,
                elevation = 4.dp,
                contentColor = Color.Black
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            tasks.forEach { task ->
                TaskCard(task = task, color = MyTasksColor, viewModel = viewModel, appContext = appContext)
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}







