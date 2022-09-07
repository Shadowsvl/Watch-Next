package com.heka.watchnext.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.heka.watchnext.ui.theme.WatchNextTheme

@Composable
fun WatchNextApp() {
    WatchNextTheme {
        val navController = rememberNavController()
        val systemUiController = rememberSystemUiController()

        val surfaceColor = MaterialTheme.colors.surface

        SideEffect {
            systemUiController.setSystemBarsColor(surfaceColor, false)
        }

        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colors.background
        ) {
            WatchNextNavGraph(
                navController = navController
            )
        }
    }
}