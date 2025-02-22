package com.shreyash16b.bookpedia

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform