package com.b1nd.dodam.teacher

import platform.Foundation.NSBundle

actual fun getPlatformName(): String {
    return PlatformModel.IOS.toString()
}