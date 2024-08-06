package com.b1nd.dodam.common.utiles

import platform.Foundation.NSString
import platform.Foundation.stringWithFormat

actual fun String.Companion.javaFormat(format: String, vararg args: Any?): String =
    NSString.stringWithFormat(format, args)