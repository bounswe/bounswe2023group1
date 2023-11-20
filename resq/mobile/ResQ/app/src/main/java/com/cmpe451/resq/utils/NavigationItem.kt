package com.cmpe451.resq.utils

enum class NavigationItem(val route: String) {
    Login (route = "login"),

    Register (route = "register"),

    Profile(route = "profile"),

    Map(route = "map"),

    Settings(route = "settings"),

    Notifications(route = "notifications"),

    Request(route = "request"),

    OngoingTasks(route = "ongoingTasks"),

    Resource(route = "resource");

    companion object {
        private val routeToBottomNavigationItemMap = mapOf(
            Profile.route to BottomNavigationItem.Profile,
            Map.route to BottomNavigationItem.Map,
            Notifications.route to BottomNavigationItem.Notifications,
            Settings.route to BottomNavigationItem.Settings
        )

        fun getBottomNavigationItem(route: String) = routeToBottomNavigationItemMap[route]
    }
}
