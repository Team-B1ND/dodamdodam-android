package com.b1nd.dodam.keystore

import android.security.keystore.KeyProperties
import android.security.keystore.KeyProtection
import android.util.Base64
import java.security.KeyStore
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.spec.IvParameterSpec
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class KeyStoreManager @Inject constructor() {
    init {
        KeyStore.getInstance(KEY_PROVIDER).apply {
            load(null)
        }.setEntry(
            KEY_ALIAS,
            KeyStore.SecretKeyEntry(
                KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES).apply {
                    init(256)
                }.generateKey(),
            ),
            KeyProtection.Builder(KeyProperties.PURPOSE_DECRYPT or KeyProperties.PURPOSE_ENCRYPT)
                .setBlockModes(KeyProperties.BLOCK_MODE_CBC)
                .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_PKCS7)
                .build(),
        )
    }

    fun encrypt(plainText: String): String {
        val keyStore = KeyStore.getInstance(KEY_PROVIDER).apply {
            load(null)
        }
        val secretKey = keyStore.getKey(KEY_ALIAS, null)

        val cipher = Cipher.getInstance(CIPHER_OPTION).apply {
            init(Cipher.ENCRYPT_MODE, secretKey)
        }

        val encryptedText = Base64.encodeToString(cipher.doFinal(plainText.toByteArray()), Base64.DEFAULT)
        val iv = Base64.encodeToString(cipher.iv, Base64.DEFAULT)

        return "$encryptedText.$iv"
    }

    fun decrypt(encryptedText: String): String {
        val keyStore = KeyStore.getInstance(KEY_PROVIDER).apply {
            load(null)
        }
        val secretKey = keyStore.getKey(KEY_ALIAS, null)

        val splitEncryptedText = encryptedText.split(".")
        val encryptTarget = Base64.decode(splitEncryptedText[0], Base64.DEFAULT)
        val iv = Base64.decode(splitEncryptedText[1], Base64.DEFAULT)

        val cipher = Cipher.getInstance(CIPHER_OPTION).apply {
            init(Cipher.DECRYPT_MODE, secretKey, IvParameterSpec(iv))
        }

        return String(cipher.doFinal(encryptTarget))
    }

    companion object {
        private const val KEY_PROVIDER = "AndroidKeyStore"

        private const val KEY_ALIAS = "DODAMDODAM_KEY"

        private const val CIPHER_OPTION = "AES/CBC/PKCS7Padding"
    }
}
