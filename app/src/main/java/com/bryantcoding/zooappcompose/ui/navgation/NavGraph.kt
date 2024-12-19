package com.bryantcoding.zooappcompose.ui.navgation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
            val zooAreas by zooAreaViewModel.zooInfo.collectAsState()
            Log.d("NavGraph:ZooAreasScreen", "NavGraph: $zooAreas")
            ZooAreasScreen(navController, zooAreas)
        }
        composable(Route.ZooAreaDetailScreen.route) {
            val zooAreaDetailViewModel = hiltViewModel<ZooAreaDetailViewModel>()
            val zooAreaDetail by zooAreaDetailViewModel.zooAreaDetail.collectAsState()
            val animalListState by zooAreaDetailViewModel.animalList.collectAsState()
            ZooAreaDetailScreen(navController, zooAreaDetail, animalListState)
        }
        composable(Route.WebViewScreen.route) { backStackEntry ->
            val url = backStackEntry.arguments?.getString("url") ?: ""
            WebViewScreen(navController, url = url)
        }
        composable(Route.AnimalDetailScreen.route) {
            val animalViewModel = hiltViewModel<AnimalViewModel>()
            val animalDetail by animalViewModel.animal.collectAsState()
            AnimalDetailScreen(navController, animalDetail)
        }

    }
}