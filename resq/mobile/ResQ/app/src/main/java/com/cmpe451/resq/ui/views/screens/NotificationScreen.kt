package com.cmpe451.resq.ui.views.screens

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.cmpe451.resq.data.models.NotificationItem
import com.cmpe451.resq.viewmodels.NotificationViewModel

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun NotificationScreen(navController: NavController, appContext: Context) {
    val viewModel = NotificationViewModel(appContext)
    val notifications by viewModel.notificationItems

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Notifications") },
                navigationIcon = {
                    Icon(
                        Icons.Default.Notifications,
                        contentDescription = "Notifications"
                    )
                },
                backgroundColor = Color.White,
                contentColor = Color.Black
            )
        }
    ) {
        NotificationList(notifications)
    }
}

@Composable
fun NotificationList(notificationItems: List<NotificationItem>) {
    LazyColumn {
        items(notificationItems) { item ->
            NotificationItemCard(item)
        }
    }
}

@Composable
fun NotificationItemCard(notification: NotificationItem) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = 2.dp
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Replace this with an actual image using Coil or other image loading library
                Icon(
                    Icons.Default.AccountCircle,
                    contentDescription = "Profile",
                    modifier = Modifier.size(40.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Column {
                    Text(
                        text = notification.title,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = notification.createdAt,
                        style = MaterialTheme.typography.body2
                    )
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = notification.body,
                    style = MaterialTheme.typography.body2
                )
                /*
                if (notification.isActionable) {
                    Button(onClick = { /* TODO: Handle View action */ }) {
                        Text("VIEW")
                    }
                }
                 */
            }
        }
    }
}