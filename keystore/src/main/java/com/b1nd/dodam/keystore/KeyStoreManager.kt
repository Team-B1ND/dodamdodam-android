package com.b1nd.dodam.keystore

import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import com.b1nd.dodam.keystore.util.decode
import com.b1nd.dodam.keystore.util.encode
import java.security.KeyStore
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.spec.IvParameterSpec
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class KeyStoreManager @Inject constructor() {

    private val keyStore = KeyStore.getInstance(KEY_PROVIDER).apply {
        load(null)
    }

    private val secretKey = if (keyStore.containsAlias(KEY_ALIAS)) {
        val secretKeyEntry = keyStore.getEntry(KEY_ALIAS, null) as KeyStore.SecretKeyEntry
        secretKeyEntry.secretKey
    } else {
        val keyGenerator = KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, KEY_PROVIDER)
        val parameterSpec = KeyGenParameterSpec.Builder(
            KEY_ALIAS,
            KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT
        ).run {
            setBlockModes(KeyProperties.BLOCK_MODE_CBC)
            setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_PKCS7)
            setDigests(KeyProperties.DIGEST_SHA256)
            setUserAuthenticationRequired(false)
            build()
        }
        keyGenerator.init(parameterSpec)
        keyGenerator.generateKey()
    }

    @Synchronized
    fun encrypt(plainText: String): String {
        val cipher = Cipher.getInstance(CIPHER_OPTION).apply {
            init(Cipher.ENCRYPT_MODE, secretKey)
        }
        val encryptedText = cipher.doFinal(plainText.toByteArray())

        return "${encryptedText.encode()}.${cipher.iv.encode()}"
    }

    @Synchronized
    fun decrypt(encryptedText: String): String {
        val splitEncryptedText = encryptedText.split(".")

        val encryptTarget = splitEncryptedText[0].decode()
        val iv = splitEncryptedText[1].decode()

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
