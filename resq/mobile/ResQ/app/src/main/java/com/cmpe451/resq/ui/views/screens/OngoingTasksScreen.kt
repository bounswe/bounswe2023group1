package com.cmpe451.resq.ui.views.screens

import android.content.Context
import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.cmpe451.resq.data.models.Action
import com.cmpe451.resq.data.models.Task
import com.cmpe451.resq.ui.theme.OngoingTasksColor
import com.cmpe451.resq.viewmodels.OngoingTasksViewModel

@Composable
fun OngoingTasksScreen(navController: NavController, appContext: Context) {
    val viewModel: OngoingTasksViewModel = viewModel()
    val tasks by viewModel.tasks
    val scrollState = rememberScrollState()

    LaunchedEffect(key1 = true) {
        viewModel.getTasks(appContext)
    }
    Log.d("OngoingTasksScreen", "tasks: $tasks")
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Ongoing Tasks", color = OngoingTasksColor) },
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
                OngoingTaskCard(task = task)
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}

@Composable
fun OngoingTaskCard(task: Task) {
    val borderColor = OngoingTasksColor
    var isExpanded by remember { mutableStateOf(false) }
    val actionLabelColor = OngoingTasksColor

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clickable { isExpanded = !isExpanded },
        elevation = if (isExpanded) 4.dp else 0.dp,
        shape = RoundedCornerShape(8.dp),
        border = BorderStroke(width = if (isExpanded) 2.dp else 0.dp, color = borderColor)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(all = 16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = if (isExpanded) Icons.Filled.KeyboardArrowUp else Icons.Filled.KeyboardArrowDown,
                    contentDescription = "Expand",
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = task.description.orEmpty(),
                    style = MaterialTheme.typography.body1
                )
            }
            if (isExpanded) {
                Spacer(modifier = Modifier.height(8.dp))
                Divider()
                Spacer(modifier = Modifier.height(8.dp))
                Text("Id: ${task.id}")
                Spacer(modifier = Modifier.height(4.dp))
                Text("Urgency: ${task.urgency}")
                Spacer(modifier = Modifier.height(4.dp))
                Text("Status: ${task.status}")
                Spacer(modifier = Modifier.height(4.dp))
                task.createdDate?.let {
                    Text("Created: ${convertToReadableDate(it)}")
                }

                task.actions?.let { actions ->
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        "Actions:",
                        style = MaterialTheme.typography.subtitle1.copy(color = actionLabelColor, fontWeight = FontWeight.Bold)
                    )
                    Divider()
                    actions.forEach { action ->
                        ActionItem(action = action)
                        Spacer(modifier = Modifier.height(4.dp))
                    }
                }
            }
        }
    }
}


@Composable
fun ActionItem(action: Action) {
    var isActionExpanded by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { isActionExpanded = !isActionExpanded },
        elevation = if (isActionExpanded) 2.dp else 0.dp
    ) {
        Column(
            modifier = Modifier
                .padding(8.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = if (isActionExpanded) Icons.Filled.KeyboardArrowUp else Icons.Filled.KeyboardArrowDown,
                    contentDescription = "Expand Action",
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = action.description.orEmpty(),
                    style = MaterialTheme.typography.subtitle1
                )
            }
            if (isActionExpanded) {
                Spacer(modifier = Modifier.height(4.dp))
                Text("Id: ${action.id}")
                Spacer(modifier = Modifier.height(4.dp))
                Text("Start Coordinates: ${action.startLatitude}, ${action.startLongitude}")
                Spacer(modifier = Modifier.height(4.dp))
                Text("End Coordinates: ${action.endLatitude}, ${action.endLongitude}")
                Spacer(modifier = Modifier.height(4.dp))
                action.createdDate?.let {
                    Text("Created: ${convertToReadableDate(it)}")
                }
                action.dueDate?.let {
                    Text("Created: ${convertToReadableDate(it)}")
                }
                Spacer(modifier = Modifier.height(4.dp))
                Text("Completed: ${action.completed}")
            }
        }
    }
}


