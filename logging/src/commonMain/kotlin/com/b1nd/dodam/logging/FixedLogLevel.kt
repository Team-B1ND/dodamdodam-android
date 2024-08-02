package com.b1nd.dodam.logging

class FixedLogLevel(private val isLogging: Boolean) : LogLevelController {
    override fun isLoggingVerbose() = isLogging
    override fun isLoggingDebug() = isLogging
    override fun isLoggingInfo() = isLogging
    override fun isLoggingWarning() = isLogging
    override fun isLoggingError() = isLogging

    override fun toString(): String {
        return "FixedLogLevel(isLogging=$isLogging)"
    }
}
