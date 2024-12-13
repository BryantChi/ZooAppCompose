package com.bryantcoding.zooappcompose.ui.components

import android.net.Uri
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

@Composable
fun WebViewScreen(navController: NavController, url: String) {

    val decodedUrl = Uri.decode(url)

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
            AndroidView(factory = { context ->
                WebView(context).apply {
                    webViewClient = object : WebViewClient() {
                        override fun onPageFinished(view: WebView, url: String) {
                            super.onPageFinished(view, url)
                        }
                        override fun shouldOverrideUrlLoading(view: WebView, request: WebResourceRequest): Boolean {
                            return super.shouldOverrideUrlLoading(view, request)
                        }
                    }
                    this.settings.javaScriptEnabled = true
                    this.settings.allowFileAccess = false
                    this.settings.allowContentAccess = false
                    loadUrl(decodedUrl)
                }
            })
        }
    }


}