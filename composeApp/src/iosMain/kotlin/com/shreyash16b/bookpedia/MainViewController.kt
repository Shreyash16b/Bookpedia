package com.shreyash16b.bookpedia

import androidx.compose.runtime.remember
import androidx.compose.ui.window.ComposeUIViewController
import com.shreyash16b.bookpedia.di.initKoin
import io.ktor.client.engine.darwin.Darwin

//fun MainViewController() = ComposeUIViewController { App() }
fun MainViewController() = ComposeUIViewController(
    configure = {
        initKoin()
    }
) { App() }