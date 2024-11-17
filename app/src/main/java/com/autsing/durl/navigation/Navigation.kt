package com.autsing.durl.navigation

import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController

class Navigation(
    private val navController: NavHostController,
    private val navDestinations: List<Destination>,
    private val startDestination: Destination? = null,
) {
    fun getStartRoute(): String {
        return startDestination?.route ?: navDestinations.first().route
    }

    fun getCurrentRoute(): String {
        return navController.currentBackStackEntry?.destination?.route
            ?: navDestinations.first().route
    }

    fun navigateTo(route: String) {
        if (getCurrentRoute() == route) {
            return
        }
        navController.navigate(route) {
            popUpTo(navController.graph.findStartDestination().id)
            launchSingleTop = true
        }
    }

    fun navigateBack() {
        if (getCurrentRoute() == getStartRoute()) {
            return
        }
        navController.popBackStack()
    }
}
