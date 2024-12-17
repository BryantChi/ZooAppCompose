package com.bryantcoding.zooappcompose.ui.navgation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.bryantcoding.zooappcompose.ui.components.WebViewScreen
import com.bryantcoding.zooappcompose.ui.screen.AnimalDetailScreen
import com.bryantcoding.zooappcompose.ui.screen.ZooAreaDetailScreen
import com.bryantcoding.zooappcompose.ui.screen.ZooAreasScreen
import com.bryantcoding.zooappcompose.ui.viewmodel.AnimalViewModel
import com.bryantcoding.zooappcompose.ui.viewmodel.ZooAreaDetailViewModel
import com.bryantcoding.zooappcompose.ui.viewmodel.ZooAreaViewModel

@Composable
fun NavGraph() {
    val navController = rememberNavController()


    NavHost(navController = navController, startDestination = Route.ZooAreasScreen.route) {
        composable(Route.ZooAreasScreen.route) {
            val zooAreaViewModel = hiltViewModel<ZooAreaViewModel>()
            ZooAreasScreen(navController,zooAreaViewModel)
        }
        composable(Route.ZooAreaDetailScreen.route) {
            val zooAreaDetailViewModel = hiltViewModel<ZooAreaDetailViewModel>()
            ZooAreaDetailScreen(navController, zooAreaDetailViewModel)
        }
        composable(Route.WebViewScreen.route) { backStackEntry ->
            val url = backStackEntry.arguments?.getString("url") ?: ""
            WebViewScreen(navController, url = url)
        }
        composable(Route.AnimalDetailScreen.route) {
            val animalViewModel = hiltViewModel<AnimalViewModel>()
            AnimalDetailScreen(navController, animalViewModel)
        }

    }
}