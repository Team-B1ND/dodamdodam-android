package com.b1nd.dodam.register.datasource

import com.b1nd.dodam.register.model.ChildrenRequest


interface RegisterDataSource {
    suspend fun register(email: String, grade: Int, id: String, name: String, number: Int, phone: String, pw: String, room: Int)

    suspend fun registerTeacher(id: String, email: String, name: String, phone: String, pw: String, position: String, tel: String)

    suspend fun registerParent(id: String, pw: String, name: String, childrenList: List<ChildrenRequest>, phone: String)
}
