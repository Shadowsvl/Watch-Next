package com.heka.watchnext.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.heka.watchnext.ui.theme.WatchNextTheme

@Composable
fun WatchNextApp() {
    WatchNextTheme {
        val navController = rememberNavController()

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