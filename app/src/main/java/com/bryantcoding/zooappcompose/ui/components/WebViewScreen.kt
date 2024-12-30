package com.bryantcoding.zooappcompose.ui.components

import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import com.bryantcoding.zooappcompose.config.Config

@Composable
fun WebViewScreen(navController: NavController, url: String) {

    Scaffold(
        topBar = {
            CustomTopBar(
                title = "WebView",
                navController = navController,
                isBack = true
            )
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            val trustedUrls = listOf(Config.BASE_URL)
            AndroidView(factory = { context ->
                WebView(context).apply {
                    webViewClient = object : WebViewClient() {
                        override fun onPageFinished(view: WebView, url: String) {
                            super.onPageFinished(view, url)
                        }
                        override fun shouldOverrideUrlLoading(view: WebView, request: WebResourceRequest): Boolean {
                            return if (trustedUrls.contains(url)) {
                                super.shouldOverrideUrlLoading(view, request)
                            } else {
                                // Handle untrusted URLs
                                true
                            }
                        }
                    }
                    settings.javaScriptEnabled = true
                    settings.allowFileAccess = false
                    settings.allowContentAccess = false
                    loadUrl(url)
                }
            })
        }
    }


}