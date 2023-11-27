package com.cmpe451.resq.ui.views.screens

import android.content.Context
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.cmpe451.resq.data.manager.UserSessionManager
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
