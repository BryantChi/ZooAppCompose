package com.bryantcoding.zooappcompose.ui.navgation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.bryantcoding.zooappcompose.ui.screen.ZooAreasScreen
import com.bryantcoding.zooappcompose.viewmodel.ZooAreaViewModel

@Composable
fun NavGraph() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Route.ZooAreasScreen.route) {
        composable(Route.ZooAreasScreen.route) {
            val zooAreaViewModel: ZooAreaViewModel = hiltViewModel()
            ZooAreasScreen(zooAreaViewModel)
        }
//        composable(Route.ZooAreaDetailScreen.route) {
//            ZooAreaDetailScreen(navController)
//        }

    }
}