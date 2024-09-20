package com.b1nd.dodam.logging

expect class PlatformLogger(logLevel: LogLevelController) : Logger, TagProvider {
    override fun verbose(tag: String, msg: String)
    override fun debug(tag: String, msg: String)
    override fun info(tag: String, msg: String)
    override fun warn(tag: String, msg: String, t: Throwable?)
    override fun error(tag: String, msg: String, t: Throwable?)
    override fun createTag(fromClass: String?): Pair<String, String>
    val logLevel: LogLevelController

    override fun isLoggingVerbose(): Boolean
    override fun isLoggingDebug(): Boolean
    override fun isLoggingError(): Boolean
    override fun isLoggingInfo(): Boolean
    override fun isLoggingWarning(): Boolean
}
