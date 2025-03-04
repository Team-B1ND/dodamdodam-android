package com.b1nd.dodam.register.datasource

interface RegisterDataSource {
    suspend fun register(email: String, grade: Int, id: String, name: String, number: Int, phone: String, pw: String, room: Int)

    suspend fun registerTeacher(id: String, email: String, name: String, phone: String, pw: String, position: String, tel: String)
}
