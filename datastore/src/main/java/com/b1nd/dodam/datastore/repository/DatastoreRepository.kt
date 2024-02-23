package com.b1nd.dodam.datastore.repository

import androidx.datastore.core.DataStore
import com.b1nd.dodam.datastore.model.User
import com.b1nd.dodam.keystore.KeyStoreManager
import javax.inject.Inject
import kotlinx.coroutines.flow.map

class DatastoreRepository @Inject constructor(
    private val dataStore: DataStore<User>,
    private val keyStoreManager: KeyStoreManager,
) {
    val user = dataStore.data.map {
        User(
            id = keyStoreManager.decrypt(it.id),
            pw = keyStoreManager.decrypt(it.pw),
            token = it.token,
        )
    }
    suspend fun saveUser(id: String, pw: String, token: String) {
        dataStore.updateData { user ->
            user.copy(
                id = keyStoreManager.encrypt(id),
                pw = keyStoreManager.encrypt(pw),
                token = token,
            )
        }
    }
}
