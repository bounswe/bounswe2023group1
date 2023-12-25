package com.cmpe451.resq

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.cmpe451.resq.data.manager.UserSessionManager
import com.cmpe451.resq.ui.theme.LightGreen
import com.cmpe451.resq.ui.theme.ResQTheme
import com.cmpe451.resq.ui.views.screens.LoginScreen
import com.cmpe451.resq.ui.views.screens.MapScreen
import com.cmpe451.resq.ui.views.screens.NotificationScreen
import com.cmpe451.resq.ui.views.screens.OngoingTasksScreen
import com.cmpe451.resq.ui.views.screens.ProfileScreen
import com.cmpe451.resq.ui.views.screens.RegistrationScreen
import com.cmpe451.resq.ui.views.screens.RequestScreen
import com.cmpe451.resq.ui.views.screens.ResourceScreen
import com.cmpe451.resq.ui.views.screens.SettingsScreen
import com.cmpe451.resq.ui.views.screens.TasksScreen
import com.cmpe451.resq.utils.BottomNavigationItem
import com.cmpe451.resq.utils.NavigationItem
import com.cmpe451.resq.viewmodels.MapViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.cmpe451.resq.ui.views.screens.MyRequestsScreen
class MainActivity : ComponentActivity() {

    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                mapViewModel.getDeviceLocation(fusedLocationProviderClient)
            }
        }

    private fun askPermissions() = when {
        ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED -> {
            mapViewModel.getDeviceLocation(fusedLocationProviderClient)
        }
        else -> {
            requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        }
    }

    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private val mapViewModel: MapViewModel by viewModels()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        askPermissions()
        setContent {
            val appContext = applicationContext
            ResQTheme {
                MainScreen(appContext, mapViewModel)
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(appContext: Context, mapViewModel: MapViewModel) {
    val navController = rememberNavController()

    mapViewModel.saveLastKnownLocation(appContext)

    Scaffold(
        bottomBar = { BottomNavigationBar(navController, appContext) },
        content = { paddingValues ->
            Box(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize()
            ) {
                NavGraph(
                    navController = navController,
                    appContext = appContext,
                    mapViewModel = mapViewModel
                )
            }
        },)
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NavGraph(
    navController: NavHostController,
    appContext: Context,
    mapViewModel: MapViewModel,
) {
    NavHost(
        navController = navController,
        startDestination = NavigationItem.Map.route
    ) {
        composable(NavigationItem.Register.route) {
            RegistrationScreen(navController, appContext)
        }
        composable(NavigationItem.Login.route) {
            LoginScreen(navController, appContext)
        }
        composable(NavigationItem.Map.route) {
            MapScreen(navController, appContext, mapViewModel)
        }
        composable(NavigationItem.Request.route) {
            RequestScreen(navController, appContext)
        }
        composable(NavigationItem.Resource.route) {
            ResourceScreen(navController, appContext)
        }
        composable(NavigationItem.Task.route) {
            TasksScreen(navController)
        }
        composable(NavigationItem.OngoingTasks.route) {
            OngoingTasksScreen(navController)
        }
        composable(NavigationItem.Profile.route) {
            ProfileScreen(navController, appContext)
        }
        composable(NavigationItem.Notifications.route) {
            NotificationScreen(navController, appContext)
        }
        composable(NavigationItem.Settings.route) {
            SettingsScreen(navController, appContext)
        }
        composable(NavigationItem.MyRequestsScreen.route) {
            MyRequestsScreen(navController, appContext)
        }
    }
}

@Composable
fun BottomNavigationBar(navController: NavController, appContext: Context) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route ?: return
    val currentBottomNavigation = NavigationItem.getBottomNavigationItem(currentRoute) ?: return

    BottomNavigation(
        backgroundColor = Color.White
    ) {
        BottomNavigationItem.values().forEach {
            BottomNavigationItem(
                selected = currentBottomNavigation == it,
                enabled = UserSessionManager.getInstance(appContext).isLoggedIn(),
                onClick = {
                    navController.navigate(it.navigation.route) {
                        navController.graph.startDestinationRoute?.let { route ->
                            popUpTo(route) {
                                if (currentBottomNavigation != it) {
                                    saveState = true
                                }
                            }
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                icon = { Icon(imageVector = it.icon, contentDescription = null) },
                label = {
                    if (currentBottomNavigation == it) {
                        Text(it.title)
                    }
                },
                alwaysShowLabel = false,
                selectedContentColor = LightGreen,
            )
        }
    }
}
