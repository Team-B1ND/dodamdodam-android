package com.b1nd.dodam.register.repository

import com.b1nd.dodam.common.result.Result
import kotlinx.coroutines.flow.Flow

interface RegisterRepository {
    fun register(email: String, grade: Int, id: String, name: String, number: Int, phone: String, pw: String, room: Int): Flow<Result<Unit>>
}
