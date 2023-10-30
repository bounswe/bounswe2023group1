package com.cmpe451.resq.utils

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector


enum class BottomNavigationItem(
    val navigation: NavigationItem,
    val title: String,
    val icon: ImageVector,
) {
    Profile(
        navigation = NavigationItem.Profile,
        title = "Profile",
        icon = Icons.Default.Person,
    ),

    Map(
        navigation = NavigationItem.Map,
        title = "Map",
        icon = Icons.Default.LocationOn,
    ),

    Notifications(
        navigation = NavigationItem.Notifications,
        title = "Notif",
        icon = Icons.Default.Notifications,
    ),

    Settings(
        navigation = NavigationItem.Settings,
        title = "Settings",
        icon = Icons.Default.Settings,
    ),
}
