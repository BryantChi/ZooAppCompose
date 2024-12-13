package com.bryantcoding.zooappcompose.ui.navgation

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.bryantcoding.zooappcompose.ui.components.WebViewScreen
import com.bryantcoding.zooappcompose.ui.screen.ZooAreaDetailScreen
import com.bryantcoding.zooappcompose.ui.screen.ZooAreasScreen
import com.bryantcoding.zooappcompose.ui.theme.ZooAppComposeTheme
import com.bryantcoding.zooappcompose.viewmodel.ZooAreaViewModel

@Composable
fun NavGraph() {
    val navController = rememberNavController()
    val zooAreaViewModel = hiltViewModel<ZooAreaViewModel>()

    NavHost(navController = navController, startDestination = Route.ZooAreasScreen.route) {
        composable(Route.ZooAreasScreen.route) {
            ZooAreasScreen(navController,zooAreaViewModel)
        }
        composable(Route.ZooAreaDetailScreen.route) {
            ZooAreaDetailScreen(navController, zooAreaViewModel)
        }
        composable(Route.WebViewScreen.route) { backStackEntry ->
            val url = backStackEntry.arguments?.getString("url") ?: ""
            WebViewScreen(navController, url = url)
        }

    }
}