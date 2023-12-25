package com.cmpe451.resq.ui.views.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.cmpe451.resq.data.models.Action
import com.cmpe451.resq.ui.views.screens.convertToReadableDate

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