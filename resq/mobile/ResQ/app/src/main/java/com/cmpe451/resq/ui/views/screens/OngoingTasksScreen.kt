package com.cmpe451.resq.ui.views.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.cmpe451.resq.ui.theme.OngoingTasksColor
import com.cmpe451.resq.viewmodels.OngoingTasksViewModel

@Composable
fun OngoingTasksScreen(navController: NavController) {
    val viewModel: OngoingTasksViewModel = viewModel()
    val tasks by viewModel.tasks.collectAsState()

    Scaffold(
        topBar = {TopAppBar(
            title = { Text(text = "Ongoing Tasks",
                color = Color(0xFFE16834)) },
            navigationIcon = {
                IconButton(onClick = { navController.navigateUp() }) {
                    Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
                }
            },
            backgroundColor = Color.White,
            elevation = 4.dp,
            contentColor = Color.Black
        )}

    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            tasks.forEachIndexed { index, task ->
                OngoingTaskCard(taskNumber = index + 1, taskDescription = task)
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}

@Composable
fun OngoingTaskCard(taskNumber: Int, taskDescription: String) {
    val borderColor = Color(0xFFE16834) // Color for the border and hashtag
    val taskColor = OngoingTasksColor // Color for the task description
    var isSelected by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clickable { isSelected = !isSelected },
        elevation = if (isSelected) 4.dp else 0.dp, // Adjust elevation based on isSelected state
        shape = RoundedCornerShape(8.dp),
        border = BorderStroke(width = if (isSelected) 2.dp else 0.dp, color = borderColor)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(all = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "#$taskNumber",
                style = MaterialTheme.typography.body1,
                color = borderColor
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = taskDescription,
                style = MaterialTheme.typography.body1,
                color = taskColor
            )
        }
    }
}

