package com.shreyash16b.bookpedia

import androidx.compose.runtime.remember
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.shreyash16b.bookpedia.di.initKoin
import io.ktor.client.engine.okhttp.OkHttp

fun main() {
    initKoin()
    application {
        Window(
            onCloseRequest = ::exitApplication,
            title = "Bookpedia",
        ) {
            App()
        }
    }
}