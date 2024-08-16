package com.b1nd.dodam.keystore

import com.b1nd.dodam.ios.keystore.Keystore
import kotlinx.cinterop.BetaInteropApi
import kotlinx.cinterop.CPointer
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.ObjCObjectVar
import kotlinx.cinterop.alloc
import kotlinx.cinterop.memScoped
import kotlinx.cinterop.pointed
import kotlinx.cinterop.ptr
import kotlinx.cinterop.value
import platform.Foundation.NSData
import platform.Foundation.NSError
import platform.Foundation.NSString
import platform.Foundation.NSUTF8StringEncoding
import platform.Foundation.dataUsingEncoding

actual class KeyStoreManager actual constructor() {
    @OptIn(ExperimentalForeignApi::class)
    actual fun encrypt(plainText: String): String {
        val text = throwError {
            Keystore.encryptDataWithData(
                data = plainText.toNSData(),
                error = it,
            )
        }

        return text ?: ""
    }

    @OptIn(ExperimentalForeignApi::class)
    actual fun decrypt(encryptedText: String): String {
        val text = throwError {
            Keystore.decryptDataWithData(
                data = encryptedText,
                error = it,
            )
        }

        return text ?: ""
    }
}

@Suppress("CAST_NEVER_SUCCEEDS")
fun String.toNSData(): NSData {
    return (this as NSString).dataUsingEncoding(NSUTF8StringEncoding)!!
}

@OptIn(ExperimentalForeignApi::class, BetaInteropApi::class)
fun <T> throwError(block: (errorPointer: CPointer<ObjCObjectVar<NSError?>>) -> T): T {
    memScoped {
        val errorPointer: CPointer<ObjCObjectVar<NSError?>> = alloc<ObjCObjectVar<NSError?>>().ptr
        val result: T = block(errorPointer)
        val error: NSError? = errorPointer.pointed.value
        if (error != null) {
            throw NSErrorException(error)
        } else {
            return result
        }
    }
}

class NSErrorException(nsError: NSError) : Exception(nsError.toString())
