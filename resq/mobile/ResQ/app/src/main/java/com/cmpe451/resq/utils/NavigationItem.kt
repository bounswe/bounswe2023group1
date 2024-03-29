package com.cmpe451.resq.utils

enum class NavigationItem(val route: String) {
    Login(route = "login"),
    Register (route = "register"),
    Profile(route = "profile"),
    Map(route = "map"),
    Settings(route = "settings"),
    Notifications(route = "notifications"),
    Request(route = "request"),
    Resource(route = "resource"),
    MyTasks(route = "myTasks"),
    MyRequestsScreen(route = "myRequests"),
    MyResourcesScreen(route = "myResources"),
    OngoingTasks(route = "ongoingTasks");

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
