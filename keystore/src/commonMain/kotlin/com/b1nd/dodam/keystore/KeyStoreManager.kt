package com.b1nd.dodam.keystore

import org.koin.dsl.module


expect class KeyStoreManager() {

    fun encrypt(plainText: String): String


    fun decrypt(encryptedText: String): String

}

val keystoreManagerModule = module {
    single<KeyStoreManager> { KeyStoreManager() }
}