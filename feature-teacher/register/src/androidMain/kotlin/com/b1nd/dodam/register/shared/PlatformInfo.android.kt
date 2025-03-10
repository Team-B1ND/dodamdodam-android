package com.b1nd.dodam.register.shared

import android.os.Build


actual fun getProductName(): String {
    return Build.PRODUCT
}
