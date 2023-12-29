package com.cmpe451.resq.ui.views.screens
import android.content.Context
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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import com.cmpe451.resq.data.models.Need
import com.cmpe451.resq.ui.theme.RequestColor
import com.cmpe451.resq.viewmodels.MyRequestsViewModel
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import java.text.SimpleDateFormat
import java.util.Locale

fun convertToReadableDate(dateStr: String): String {
    val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS", Locale.getDefault())
    val outputFormat = SimpleDateFormat("dd MMM yyyy, HH:mm", Locale.getDefault())

    return try {
        val date = inputFormat.parse(dateStr)
        date?.let { outputFormat.format(it) } ?: "Unknown Date"
    } catch (e: Exception) {
        "Invalid Date"
    }
}

@Composable
fun MyRequestsScreen(navController: NavController, appContext: Context) {
    val viewModel: MyRequestsViewModel = viewModel()
    val needs by viewModel.needs
    val scrollState = rememberScrollState()
    // A side effect to load the needs when the composable enters the composition
    LaunchedEffect(key1 = true) {
        viewModel.getNeeds(appContext)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "My Requests", color = RequestColor) },
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
            needs.forEachIndexed { index, need ->
                RequestCard(viewModel, requestNumber = index + 1, request = need, onDelete = { viewModel.deleteNeed(appContext, need.id) })
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}

@Composable
fun RequestCard(viewModel: MyRequestsViewModel, requestNumber: Int, request: Need, onDelete: () -> Unit) {
    var isSelected by remember { mutableStateOf(false) }
    val categoryName = viewModel.getCategoryName(request.categoryTreeId.toInt())
    var showConfirmationDialog by remember { mutableStateOf(false) }

    if (showConfirmationDialog) {
        AlertDialog(
            onDismissRequest = { showConfirmationDialog = false },
            title = { Text("Delete Request") },
            text = { Text("Are you sure you want to delete this request?") },
            confirmButton = {
                Button(onClick = {
                    onDelete()
                    showConfirmationDialog = false
                }) {
                    Text("Yes")
                }
            },
            dismissButton = {
                Button(onClick = { showConfirmationDialog = false }) {
                    Text("No")
                }
            }
        )
    }
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clickable { isSelected = !isSelected },
        elevation = if (isSelected) 4.dp else 0.dp,
        shape = RoundedCornerShape(8.dp),
        border = BorderStroke(width = if (isSelected) 2.dp else 0.dp, color = RequestColor)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(all = 16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "#$requestNumber",
                    style = MaterialTheme.typography.body1,
                    color =  Color(0xFF007BFF)
                )
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    text = categoryName,
                    style = MaterialTheme.typography.body1,
                    color = RequestColor
                )
            }
            if (isSelected) {
                Text("Description: ${request.description}")
                Text("Id: ${request.id}")
                Text("Quantity: ${request.quantity}")
                Text("Latitude: ${request.latitude}")
                Text("Longitude: ${request.longitude}")
                Text("Status: ${request.status}")
                Text("Created Date: ${convertToReadableDate(request.createdDate)}")
                Button(
                    onClick = { showConfirmationDialog = true },
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.Red),
                    modifier = Modifier.padding(top = 8.dp)
                ) {
                    Text("Delete Request", color = Color.White)
                }
            }
        }
    }
}
