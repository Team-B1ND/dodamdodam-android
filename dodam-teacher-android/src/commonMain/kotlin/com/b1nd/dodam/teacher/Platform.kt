package com.b1nd.dodam.teacher

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform
