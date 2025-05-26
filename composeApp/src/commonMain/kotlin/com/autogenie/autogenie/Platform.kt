package com.autogenie.autogenie

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform