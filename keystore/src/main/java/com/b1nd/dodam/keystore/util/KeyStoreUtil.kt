package com.b1nd.dodam.keystore.util

import android.util.Base64

internal fun ByteArray.encode(): String = Base64.encodeToString(this, Base64.DEFAULT)

internal fun String.decode(): ByteArray = Base64.decode(this, Base64.DEFAULT)
