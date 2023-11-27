package com.cmpe451.resq

import android.content.Context
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
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
import com.cmpe451.resq.ui.views.screens.OngoingTasksScreen
import com.cmpe451.resq.ui.views.screens.ProfileScreen
import com.cmpe451.resq.ui.views.screens.RegistrationScreen
import com.cmpe451.resq.ui.views.screens.RequestScreen
import com.cmpe451.resq.ui.views.screens.ResourceScreen
import com.cmpe451.resq.ui.views.screens.SettingsScreen
import com.cmpe451.resq.utils.BottomNavigationItem
import com.cmpe451.resq.utils.NavigationItem

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val appContext = applicationContext
            ResQTheme {
                MainScreen(appContext)
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(appContext: Context) {
    val navController = rememberNavController()

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
                    appContext = appContext
                )
            }
        },)
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NavGraph(
    navController: NavHostController,
    appContext: Context
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
            MapScreen(navController, appContext)
        }
        composable(NavigationItem.Request.route) {
            RequestScreen(navController, appContext)
        }
        composable(NavigationItem.Resource.route) {
            ResourceScreen(navController, appContext)
        }
        composable(NavigationItem.OngoingTasks.route) {
            OngoingTasksScreen(navController)
        }
        composable(NavigationItem.Profile.route) {
            ProfileScreen(navController, appContext)
        }
        composable(NavigationItem.Notifications.route) {
            //NotificationsScreen(navController)
        }
        composable(NavigationItem.Settings.route) {
            SettingsScreen(navController, appContext)
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
