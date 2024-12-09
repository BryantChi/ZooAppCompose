package com.bryantcoding.zooappcompose.ui.components

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun CustomTopBar(
    title: String,
    navController: NavController? = null, // 動態導航支援
    showBackButton: Boolean = false,
    onBackClick: (() -> Unit)? = null,
    backgroundColor: Color = MaterialTheme.colorScheme.background, // 背景色
    contentColor: Color = MaterialTheme.colorScheme.onBackground, // 內容顏色
    actions: @Composable (RowScope.() -> Unit)? = null // 自訂操作按鈕
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(backgroundColor)
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            if (showBackButton) {
                IconButton(
                    onClick = {
                        if (navController != null) {
                            navController.popBackStack() // 動態返回
                        } else {
                            onBackClick?.invoke() // 自訂返回邏輯
                        }
                    }
                ) {
                    if (navController != null) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back",
                            tint = contentColor
                        )
                    } else {
                        Icon(
                            imageVector = Icons.Default.Menu,
                            contentDescription = "Back",
                            tint = contentColor
                        )
                    }
                }
            }
            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge,
                color = contentColor,
                modifier = Modifier.padding(start = if (showBackButton) 8.dp else 0.dp)
            )
        }

        // 可選的操作按鈕
        Row(
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically
        ) {
            actions?.invoke(this)
        }
    }
}