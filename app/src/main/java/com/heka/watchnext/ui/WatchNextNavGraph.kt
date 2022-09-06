package com.heka.watchnext.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.heka.watchnext.ui.screens.home.HomeScreen

object Routes {
    const val HOME = "home"
}

@Composable
fun WatchNextNavGraph(
    navController: NavHostController = rememberNavController()
) {
    val actions = remember(navController) { NavigationActions(navController = navController) }

    NavHost(
        navController = navController,
        startDestination = Routes.HOME
    ) {

        composable(route = Routes.HOME) {
            HomeScreen()
        }

    }
}

private class NavigationActions(navController: NavHostController) {

    val navigateBack: () -> Unit = {
        navController.navigateUp()
    }
}