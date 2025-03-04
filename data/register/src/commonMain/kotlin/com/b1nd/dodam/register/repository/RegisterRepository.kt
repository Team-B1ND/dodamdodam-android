package com.b1nd.dodam.register.repository

import com.b1nd.dodam.common.result.Result
import kotlinx.coroutines.flow.Flow

interface RegisterRepository {
    suspend fun register(email: String, grade: Int, id: String, name: String, number: Int, phone: String, pw: String, room: Int): Flow<Result<Unit>>

    suspend fun registerTeacher(id: String, email: String, name: String, phone: String, pw: String, position: String, tel: String): Flow<Result<Unit>>
}
