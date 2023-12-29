package com.cmpe451.resq.ui.views.screens

import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.cmpe451.resq.data.manager.UserSessionManager

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(navController: NavController, appContext: Context) {
    Column(modifier = Modifier.padding(16.dp)) {
        TopAppBar(
            navigationIcon = {
                IconButton(onClick = { navController.navigateUp() }) {
                    Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
                }
            },
            title = {
                androidx.compose.material3.Text(
                    text = "Settings",
                    style = TextStyle(
                        fontSize = 25.sp,
                        color = Color(0xFF224957),
                        textAlign = TextAlign.Center
                    ),
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            },
            modifier = Modifier.fillMaxWidth()
        )
        Button(
            onClick = { handleLogout(appContext, navController) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Color.Transparent,
                contentColor = Color.Black
            ),
            elevation = ButtonDefaults.elevation( // Set all elevations to zero
                defaultElevation = 0.dp,
                pressedElevation = 0.dp,
                disabledElevation = 0.dp
            )
        ) {
            Text(
                text = "Logout",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                textAlign = TextAlign.Left,
                fontWeight = FontWeight.Normal,
                fontSize = 18.sp
            )
        }
    }
}

private fun handleLogout(appContext: Context, navController: NavController) {
    // Clear session data
    UserSessionManager.getInstance(appContext).logout()

    // Close the app
    exitApp(navController)
}

private fun exitApp(navController: NavController) {
    // Navigate to the root (start destination) of your NavGraph
    navController.popBackStack(navController.graph.startDestinationId, false)

}

