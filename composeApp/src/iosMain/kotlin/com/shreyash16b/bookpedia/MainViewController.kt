package com.shreyash16b.bookpedia

import androidx.compose.ui.window.ComposeUIViewController
import com.shreyash16b.bookpedia.app.App
import com.shreyash16b.bookpedia.di.initKoin

//fun MainViewController() = ComposeUIViewController { App() }
fun MainViewController() = ComposeUIViewController(
    configure = {
        initKoin()
    }
) { App() }