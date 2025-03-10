package com.b1nd.dodam.register.shared

import platform.UIKit.UIDevice

actual fun getProductName(): String {
    return UIDevice.currentDevice.model
}
