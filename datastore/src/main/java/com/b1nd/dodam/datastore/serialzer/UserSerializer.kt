package com.b1nd.dodam.datastore.serialzer

import androidx.datastore.core.Serializer
import com.b1nd.dodam.datastore.model.User
import com.b1nd.dodam.keystore.KeyStoreManager
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import java.io.InputStream
import java.io.OutputStream
import javax.inject.Inject

class UserSerializer @Inject constructor(
    private val keyStoreManager: KeyStoreManager
) : Serializer<User> {
    override val defaultValue: User
        get() = User()

    override suspend fun readFrom(input: InputStream): User {
        val decryptedBytes = keyStoreManager.decrypt(input)
        return try {
            Json.decodeFromString(
                User.serializer(),
                decryptedBytes.decodeToString()
            )
        } catch (e: SerializationException) {
            e.printStackTrace()
            defaultValue
        }
    }

    override suspend fun writeTo(t: User, output: OutputStream) {
        keyStoreManager.encrypt(
            Json.encodeToString(
                serializer = User.serializer(),
                value = t).encodeToByteArray(),
            outputStream = output
        )
    }
}