package com.b1nd.dodam.logging

expect object Platform {
    val isIOS: Boolean
    val isAndroid: Boolean
    val name: String
    val version: Double
    val versionName: String
    val timeNanos: Long
}
