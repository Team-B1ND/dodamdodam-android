package com.b1nd.dodam.datastore.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.b1nd.dodam.datastore.model.User
import com.b1nd.dodam.keystore.KeyStoreManager
import kotlinx.coroutines.flow.map

class DataStoreRepositoryImpl constructor(
    private val dataStore: DataStore<Preferences>,
    private val keyStoreManager: KeyStoreManager,
) : DataStoreRepository {
    private val tokenKey = stringPreferencesKey("token")
    private val idKey = stringPreferencesKey("id")
    private val pwKey = stringPreferencesKey("pw")
    private val pushTokenKey = stringPreferencesKey("pushToken")

    override val user = dataStore.data.map {
        User(
            id = keyStoreManager.decrypt(it[idKey] ?: ""),
            pw = keyStoreManager.decrypt(it[pwKey] ?: ""),
            token = it[tokenKey] ?: "",
            pushToken = it[pushTokenKey] ?: "",
        )
    }

    override val token = dataStore.data.map {
        it[tokenKey] ?: ""
    }

    override val pushToken = dataStore.data.map {
        it[pushTokenKey] ?: ""
    }

    override suspend fun saveUser(id: String, pw: String, token: String, pushToken: String) {
        dataStore.edit {
            it[idKey] = keyStoreManager.encrypt(id)
            it[pwKey] = keyStoreManager.encrypt(pw)
            it[tokenKey] = token
            it[pushTokenKey] = pushToken
        }
    }

    override suspend fun saveToken(token: String) {
        dataStore.edit {
            it[tokenKey] = token
        }
    }

    override suspend fun savePushToken(pushToken: String) {
        dataStore.edit {
            it[pushTokenKey] = pushToken
        }
    }

    override suspend fun deleteUser() {
        dataStore.edit {
            it[idKey] = ""
            it[pwKey] = ""
            it[tokenKey] = ""
            it[pushTokenKey] = ""
        }
    }
}
