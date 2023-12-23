package com.cmpe451.resq.ui.views.screens

import android.content.Context
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonColors
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.cmpe451.resq.data.manager.UserSessionManager
import com.cmpe451.resq.data.models.Need
import com.cmpe451.resq.data.models.Resource
import com.cmpe451.resq.ui.theme.DeepBlue
import com.cmpe451.resq.ui.theme.RequestColor
import com.cmpe451.resq.ui.theme.ResourceColor
import com.cmpe451.resq.utils.NavigationItem
import com.cmpe451.resq.viewmodels.MapViewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState

@Composable
fun MapScreen(navController: NavController, appContext: Context, mapViewModel: MapViewModel) {
    val userSessionManager = UserSessionManager.getInstance(appContext)
    val userRoles = userSessionManager.getUserRoles()

    // State to keep track of which list is currently selected
    var selectedList by remember { mutableStateOf("Requests") }

    var expandedNeedId by remember { mutableStateOf<Int?>(null) }
    var expandedResourceId by remember { mutableStateOf<Int?>(null) }

    // Dummy data for the lists
    val needsList = listOf(
        Need(1, 13, "52", "Please help!", 1, 41.08, 29.05, null, "NOT_INVOLVED", "2023-11-26T02:23:04.731365"),
        Need(2, 13, "54", "Help me for god's sake", 1, 30.0, 40.0, null, "NOT_INVOLVED", "2023-11-26T10:57:10.71784")
    )
    val resourcesList = listOf(
        Resource(1, 13, 23, "52", "MALE", 10, 42.08, 30.05, "2023-11-26T02:23:04.731365", "XL"),
        Resource(2, 13, 23, "52", "MALE", 10, 42.08, 30.05, "2023-11-26T02:23:04.731365", "L"),
        Resource(3, 13, 23, "52", "MALE", 10, 42.08, 30.05, "2023-11-26T02:23:04.731365", "L"),
        Resource(4, 13, 23, "52", "MALE", 10, 42.08, 30.05, "2023-11-26T02:23:04.731365", "L"),
        Resource(5, 13, 23, "52", "MALE", 10, 42.08, 30.05, "2023-11-26T02:23:04.731365", "L"),
        Resource(6, 13, 23, "52", "MALE", 10, 42.08, 30.05, "2023-11-26T02:23:04.731365", "L")
    )

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
                if (userRoles.isNotEmpty()) {
                    if (userRoles.contains("VICTIM") || userRoles.contains("FACILITATOR")) {
                        AddRequestButton {
                            navController.navigate(NavigationItem.Request.route)
                        }
                    }
                    if (userRoles.contains("RESPONDER") || userRoles.contains("FACILITATOR")) {
                        AddResourceButton {
                            navController.navigate(NavigationItem.Resource.route)
                        }
                    }
                }
                else {
                    AddSignInButton{
                        navController.navigate(NavigationItem.Login.route)
                    }
                    AddSignUpButton{
                        navController.navigate(NavigationItem.Register.route)
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
            SearchBar(mapViewModel)
            val singapore = LatLng(41.086571, 29.046109)
            val cameraPositionState = rememberCameraPositionState {
                position = CameraPosition.fromLatLngZoom(mapViewModel.lastKnownLocation.value?.let {
                    LatLng(it.latitude, it.longitude)
                } ?: singapore, 12f)
            }
            LaunchedEffect(Unit) {
              //  mapViewModel.getNeedByDistance(appContext)
            }
            GoogleMap(
                modifier = Modifier.fillMaxSize(),
                cameraPositionState = cameraPositionState
            ) {
                mapViewModel.lastKnownLocation.value?.let {
                    val latLng = LatLng(it.latitude, it.longitude)
                    cameraPositionState.position = CameraPosition.fromLatLngZoom(latLng, 12f)
                }
                mapViewModel.needMarkerList.value.forEach { need ->
                    Marker(
                        state = MarkerState(position = LatLng(need.latitude, need.longitude)),
                        title = need.description,
                        snippet = "Quantity: ${need.quantity}"
                    )
                }
                Marker(
                    state = MarkerState(position = LatLng(41.086571, 29.046109)),
                )
            }
            LaunchedEffect(mapViewModel.needMarkerList.value) {
                if (mapViewModel.needMarkerList.value.isNotEmpty()) {
                    // Move camera to the first marker or any specific logic you want
                    val firstNeed = mapViewModel.needMarkerList.value.first()
                    cameraPositionState.move(CameraUpdateFactory.newLatLngZoom(LatLng(firstNeed.latitude, firstNeed.longitude), 12f))
                }
            }
        }
        // Requests list
        if (selectedList == "Requests") {
            LazyColumn(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .fillMaxWidth()
                    .heightIn(max = 240.dp)
                    .background(Color.White)
            ) {
                items(needsList) { need ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                            .clickable {
                                expandedNeedId = if (need.id == expandedNeedId) null else need.id
                            },
                        elevation = 4.dp
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(text = "Category ID: ${need.categoryTreeId}")
                            Text(text = "Quantity: ${need.quantity}")
                            AnimatedVisibility(visible = need.id == expandedNeedId) {
                                Column {
                                    Text(text = "User ID: ${need.userId}")
                                    Text(text = "Description: ${need.description}")
                                    Row {
                                        Button(
                                            onClick = { /* TODO: Handle Button 1 action */ },
                                            colors = ButtonDefaults.buttonColors(backgroundColor = RequestColor, contentColor = Color.White)
                                        ) {
                                            Text("Button 1")
                                        }
                                        Spacer(modifier = Modifier.width(8.dp))
                                        Button(
                                            onClick = { /* TODO: Handle Button 2 action */ },
                                            colors = ButtonDefaults.buttonColors(backgroundColor = RequestColor, contentColor = Color.White)
                                        ) {
                                            Text("Button 2")
                                        }
                                    }                                }
                            }
                        }
                    }
                }
            }
        } else { // Resources list
            LazyColumn(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .fillMaxWidth()
                    .heightIn(max = 240.dp)
                    .background(Color.White)
            ) {
                items(resourcesList) { resource ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                            .clickable {
                                expandedResourceId =
                                    if (resource.id == expandedResourceId) null else resource.id
                            },
                        elevation = 4.dp
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(text = "Category ID: ${resource.categoryTreeId}")
                            Text(text = "Quantity: ${resource.quantity}")
                            AnimatedVisibility(visible = resource.id == expandedResourceId) {
                                Column {
                                    Text(text = "User ID: ${resource.senderId}")
                                    Text(text = "Size: ${resource.size}")
                                    Row {
                                        Button(
                                            onClick = { /* TODO: Handle Button 1 action */ },
                                            colors = ButtonDefaults.buttonColors(backgroundColor = ResourceColor, contentColor = Color.White)
                                        ) {
                                            Text("Button 1")
                                        }
                                        Spacer(modifier = Modifier.width(8.dp))
                                        Button(
                                            onClick = { /* TODO: Handle Button 2 action */ },
                                            colors = ButtonDefaults.buttonColors(backgroundColor = ResourceColor, contentColor = Color.White)
                                        ) {
                                            Text("Button 2")
                                        }
                                    }                               }
                            }
                        }
                    }
                }
            }
        }
        FloatingActionButton(
            onClick = {
                selectedList = if (selectedList == "Requests") "Resources" else "Requests"
            },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp),
            backgroundColor = if (selectedList == "Requests") RequestColor else ResourceColor
        ) {
            Text(
                text = if (selectedList == "Requests") "Requests" else "Resources",
                color = Color.White,
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)

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

@Composable
fun AddSignInButton(onClick: () -> Unit) {
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(20.dp),
        modifier = Modifier
            .width(160.dp)
            .padding(8.dp),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = DeepBlue
        )
    ) {
        Text(
            "Sign In",
            color = Color.White,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun AddSignUpButton(onClick: () -> Unit) {
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(20.dp),
        modifier = Modifier
            .width(160.dp)
            .padding(8.dp),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = DeepBlue
        )
    ) {
        Text(
            "Sign Up",
            color = Color.White,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun ExpandableItemList() {
    // Mock data similar to the JSON response you showed
    val items = listOf(
        Need(1, 13, "52", "Please help!", 1, 41.08, 29.05, null, "NOT_INVOLVED", "2023-11-26T02:23:04.731365"),
        Need(2, 13, "54", "Help me for god's sake", 1, 30.0, 40.0, null, "NOT_INVOLVED", "2023-11-26T10:57:10.71784")
    )

    // State to track expanded items
    val expandedItemId = remember { mutableStateOf(-1) }

    LazyColumn {
        items(items) { item ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .clickable {
                        expandedItemId.value = if (expandedItemId.value == item.id) -1 else item.id
                    },
                elevation = 4.dp
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(text = "Category ID: ${item.categoryTreeId}")
                    Text(text = "Quantity: ${item.quantity}")

                    AnimatedVisibility(visible = expandedItemId.value == item.id) {
                        Column {
                            Text(text = "User ID: ${item.userId}")
                            Text(text = "Description: ${item.description}")
                            Row {
                                Button(onClick = { /* Handle button click */ }) {
                                    Text("Button 1")
                                }
                                Spacer(modifier = Modifier.width(8.dp))
                                Button(onClick = { /* Handle button click */ }) {
                                    Text("Button 2")
                                }
                                // Add more buttons if needed
                            }
                        }
                    }
                }
            }
        }
    }
}