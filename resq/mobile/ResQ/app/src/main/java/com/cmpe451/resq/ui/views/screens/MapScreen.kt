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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.cmpe451.resq.data.manager.UserSessionManager
import com.cmpe451.resq.ui.theme.DeepBlue
import com.cmpe451.resq.ui.theme.RequestColor
import com.cmpe451.resq.ui.theme.ResourceColor
import com.cmpe451.resq.utils.NavigationItem
import com.cmpe451.resq.viewmodels.MapViewModel
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



    mapViewModel.getAllNeeds(appContext)
    mapViewModel.getAllResources(appContext)
    // Dummy data for the lists
    val needsList = mapViewModel.needMarkerList.value
    val resourcesList = mapViewModel.resourceMarkerList.value

    LaunchedEffect(key1 = true) {
        mapViewModel.fetchMainCategories(appContext)
    }

    val categories = mapViewModel.categories.value

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
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
            Box(modifier = Modifier.height(500.dp)){
                GoogleMap(
                    modifier = Modifier.fillMaxSize(),
                    cameraPositionState = cameraPositionState
                ) {
                    mapViewModel.needMarkerList.value.forEach { need ->
                        Marker(
                            state = MarkerState(position = LatLng(need.latitude, need.longitude)),
                            title = need.description,
                            snippet = "Quantity: ${need.quantity}"

                        )
                    }
                    mapViewModel.resourceMarkerList.value.forEach { resource ->
                        Marker(
                            state = MarkerState(position = LatLng(resource.latitude, resource.longitude)),
                            title = mapViewModel.findNodeById(categories,resource.categoryTreeId.toInt())?.data,
                            snippet = "Quantity: ${resource.quantity}"
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            // Requests list
            if (selectedList == "Requests") {
                LazyColumn(
                    modifier = Modifier
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
                                    expandedNeedId =
                                        if (need.id == expandedNeedId) null else need.id
                                },
                            elevation = 4.dp
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Text(
                                    text = "${mapViewModel.findNodeById(categories, need.categoryTreeId.toInt())?.data}",
                                    fontWeight = FontWeight.Bold
                                )
                                Text(text = "Quantity: ${need.quantity}")
                                AnimatedVisibility(visible = need.id == expandedNeedId) {
                                    Column {
                                        UsernameDisplay(mapViewModel, appContext, need.userId)
                                        Text(text = "Description: ${need.description}")
                                        if (need.size != null) {
                                            Text(text = "Size: ${need.size}")
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            // Resources list
            else {
                LazyColumn(
                    modifier = Modifier
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
                                Text(
                                    text = "${mapViewModel.findNodeById(categories, resource.categoryTreeId.toInt())?.data}",
                                    fontWeight = FontWeight.Bold
                                )
                                Text(text = "Quantity: ${resource.quantity}")
                                AnimatedVisibility(visible = resource.id == expandedResourceId) {
                                    Column {
                                        UsernameDisplay(mapViewModel, appContext, resource.senderId)
                                        if (resource.size != null) {
                                            Text(text = "Size: ${resource.size}")
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
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
fun UsernameDisplay(mapViewModel: MapViewModel, appContext: Context, userId: Int) {
    val userInfo = remember { mutableStateOf("Loading...") }
    LaunchedEffect(userId) {
        mapViewModel.getUserInfoById(appContext, userId) { result ->
            userInfo.value = result
        }
    }
    Text(text = "User: ${userInfo.value}")
}

