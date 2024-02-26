package com.b1nd.dodam.register.datasource

interface RegisterDataSource {
    suspend fun register(
        email: String,
        grade: Int,
        id: String,
        name: String,
        number: Int,
        phone: String,
        pw: String,
        room: Int
    )
}