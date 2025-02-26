package com.shreyash16b.bookpedia

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.shreyash16b.bookpedia.app.App
import com.shreyash16b.bookpedia.di.initKoin

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