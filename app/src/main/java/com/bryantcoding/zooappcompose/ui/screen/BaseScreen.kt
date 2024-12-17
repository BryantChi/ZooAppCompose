package com.bryantcoding.zooappcompose.ui.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.bryantcoding.zooappcompose.ui.components.CustomTopBar

@Composable
fun BaseScreen(
    navController: NavController,
    title: String,
    isBack: Boolean = false,
    content: @Composable () -> Unit
) {
    Scaffold(
        topBar = {
            CustomTopBar(
                title = title,
                navController = navController,
                isBack = isBack
            )
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            content()
        }
    }
}