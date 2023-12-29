package com.cmpe451.resq.ui.views.screens

import android.content.Context
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.cmpe451.resq.data.models.Resource
import com.cmpe451.resq.ui.theme.RequestColor
import com.cmpe451.resq.ui.theme.ResourceColor
import com.cmpe451.resq.viewmodels.MyResourcesViewModel


@Composable
fun MyResourcesScreen(navController: NavController, appContext: Context) {
    val viewModel: MyResourcesViewModel = viewModel()
    val resources by viewModel.resources
    val scrollState = rememberScrollState()
    // A side effect to load the needs when the composable enters the composition
    LaunchedEffect(key1 = true) {
        viewModel.getMyResources(appContext)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "My Resources", color = ResourceColor) },
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
            resources.forEachIndexed { index, resource ->
                ResourceCard(viewModel, resourceNumber = index + 1, resource = resource)
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}

@Composable
fun ResourceCard(viewModel: MyResourcesViewModel, resourceNumber: Int, resource: Resource) {
    var isSelected by remember { mutableStateOf(false) }
    val categoryName = viewModel.getCategoryName(resource.categoryTreeId.toInt())

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clickable { isSelected = !isSelected },
        elevation = if (isSelected) 4.dp else 0.dp,
        shape = RoundedCornerShape(8.dp),
        border = BorderStroke(width = if (isSelected) 2.dp else 0.dp, color = ResourceColor)
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
                    text = "#$resourceNumber",
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
                Text("Quantity: ${resource.quantity}")
                Text("Latitude: ${resource.latitude}")
                Text("Longitude: ${resource.longitude}")
                Text("Created Date: ${convertToReadableDate(resource.createdDate)}")
            }
        }
    }
}