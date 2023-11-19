package com.cmpe451.resq.ui.views.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.cmpe451.resq.R
import com.cmpe451.resq.ui.theme.RequestColor
import com.cmpe451.resq.ui.theme.ResourceColor
import com.cmpe451.resq.utils.NavigationItem
import com.cmpe451.resq.viewmodels.MapViewModel

@Composable
fun MapScreen(navController: NavController) {
    val viewModel: MapViewModel = viewModel()

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                AddRequestButton {
                    navController.navigate(NavigationItem.Request.route)
                }
                AddResourceButton {
                    navController.navigate(NavigationItem.Resource.route)
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            SearchBar(viewModel)
            Image(
                painter = painterResource(id = R.drawable.mock_map),
                contentDescription = "Mock Map",
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}

@Composable
fun SearchBar(viewModel: MapViewModel) {
    OutlinedTextField(
        value = viewModel.searchQuery.value,
        onValueChange = { viewModel.searchQuery.value = it },
        placeholder = { Text("Search...") },
        leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search Icon") },
        modifier = Modifier.fillMaxWidth()
    )
}
@Composable
fun AddRequestButton(onClick: () -> Unit) {
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(20.dp),
        modifier = Modifier
            .width(160.dp)
            .padding(8.dp),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = RequestColor
        )
    ) {
        Icon(
            Icons.Default.Add,
            contentDescription = "Add Request",
            tint = Color.White
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            "Add Request",
            color = Color.White,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun AddResourceButton(onClick: () -> Unit) {
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(20.dp),
        modifier = Modifier
            .width(160.dp)
            .padding(8.dp),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = ResourceColor
        )
    ) {
        Icon(
            Icons.Default.AddCircle,
            contentDescription = "Add Resource",
            tint = Color.White
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            "Add Resource",
            color = Color.White,
            textAlign = TextAlign.Center

        )
    }
}