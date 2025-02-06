package com.b1nd.dodam.datastore.repository

import com.b1nd.dodam.datastore.model.User
import kotlinx.coroutines.flow.Flow

interface DataStoreRepository {

    val user: Flow<User>

    val token: Flow<String>

    val pushToken: Flow<String>

    suspend fun saveUser(id: String, pw: String, token: String, pushToken: String)

    suspend fun saveToken(token: String)

    suspend fun savePushToken(pushToken: String)

    suspend fun deleteUser()
}
