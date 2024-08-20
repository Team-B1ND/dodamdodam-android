package com.b1nd.dodam.common.utiles

import platform.Foundation.NSString
import platform.Foundation.stringWithFormat
import platform.darwin.NSObject

actual fun String.Companion.javaFormat(format: String, vararg args: Any?): String {
//    val nsArray = arrayOf(*args.map { it as? NSObject ?: NSNull() }.toTypedArray())//args.map { it as? NSObject ?: NSNull() } //NSString.stringWithFormat(format, args)
    return NSString.stringWithFormat(format, args.map { it as? NSObject })
}
