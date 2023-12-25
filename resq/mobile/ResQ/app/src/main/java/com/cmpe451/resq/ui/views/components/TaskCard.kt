package com.cmpe451.resq.ui.views.components

import android.content.Context
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.cmpe451.resq.data.models.Task
import com.cmpe451.resq.ui.views.screens.convertToReadableDate
import com.cmpe451.resq.viewmodels.TasksViewModel

@Composable
fun TaskCard(task: Task, color: Color, viewModel: TasksViewModel, appContext: Context) {
    val borderColor = color
    var isExpanded by remember { mutableStateOf(false) }
    val actionLabelColor = color
    val assigneeInfo by viewModel.assigneeInfo
    val assignerInfo by viewModel.assignerInfo

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
                task.assignee?.let { assignee ->
                    LaunchedEffect(assignee) {
                        viewModel.fetchAssigneeInfo(appContext, assignee)
                    }
                    Text("Assignee: ${assigneeInfo ?: "Loading..."}")
                }

                task.assigner?.let { assigner ->
                    LaunchedEffect(assigner) {
                        viewModel.fetchAssignerInfo(appContext, assigner)
                    }
                    Text("Assigner: ${assignerInfo ?: "Loading..."}")
                }
                Text("Status: ${task.status}")
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