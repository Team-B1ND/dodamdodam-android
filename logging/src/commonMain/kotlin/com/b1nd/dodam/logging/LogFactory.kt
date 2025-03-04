package com.b1nd.dodam.logging

interface LogFactory {
    fun createKmLog(tag: String, className: String): KmLog
}
