package com.example.yinyang.ui.shared.components.service

import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.isDebugInspectorInfoEnabled
import androidx.compose.ui.viewinterop.AndroidView

@Composable
fun PaymentWebView(
    paymentToken: String
) {
    if (paymentToken != "") {
        AndroidView(factory = { context ->
            WebView(context).apply {
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )

                settings.javaScriptEnabled = true
                webViewClient = WebViewClient()
                isDebugInspectorInfoEnabled = true
                loadUrl("file:///android_asset/Form.html?paymentToken=${paymentToken}")
            }
        })
    }
}