package com.b1nd.dodam.common.utiles

actual fun String.Companion.javaFormat(format: String, vararg args: Any?): String = String.format(format, args)
