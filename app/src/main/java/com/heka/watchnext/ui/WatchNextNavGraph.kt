package com.heka.watchnext.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.heka.watchnext.ui.screens.detail.DetailScreen
import com.heka.watchnext.ui.screens.home.HomeScreen
import com.heka.watchnext.ui.screens.infinite_list.InfiniteListScreen

object Routes {
    const val HOME = "home"
    const val DETAIL = "detail"
    const val INFINITE_LIST = "infiniteList"
}

object Arguments {
    const val MEDIA_ID = "mediaId"
    const val MEDIA_TYPE_NAME = "mediaTypeName"
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
            HomeScreen(
                navigateToDetail = actions.navigateToDetail,
                navigateToInfiniteList = actions.navigateToInfiniteList
            )
        }

        composable(
            route = "${Routes.DETAIL}/{${Arguments.MEDIA_ID}}/{${Arguments.MEDIA_TYPE_NAME}}",
            arguments = listOf(
                navArgument(Arguments.MEDIA_ID) { type = NavType.LongType },
                navArgument(Arguments.MEDIA_TYPE_NAME) { type = NavType.StringType }
            )
        ) {
            DetailScreen(
                onBack = actions.navigateBack,
                navigateToDetail = actions.navigateToDetail
            )
        }

        composable(
            route = "${Routes.INFINITE_LIST}/{${Arguments.MEDIA_TYPE_NAME}}",
            arguments = listOf(
                navArgument(Arguments.MEDIA_TYPE_NAME) { type = NavType.StringType }
            )
        ) {
            InfiniteListScreen(
                onBack = actions.navigateBack,
                navigateToDetail = actions.navigateToDetail
            )
        }
    }
}

private class NavigationActions(navController: NavHostController) {

    val navigateToDetail: (mediaId: Long, mediaTypeName: String) -> Unit = { mediaId, mediaTypeName ->
        navController.navigate("${Routes.DETAIL}/$mediaId/$mediaTypeName")
    }

    val navigateToInfiniteList: (mediaTypeName: String) -> Unit = { mediaTypeName ->
        navController.navigate("${Routes.INFINITE_LIST}/$mediaTypeName")
    }

    val navigateBack: () -> Unit = {
        navController.navigateUp()
    }
}