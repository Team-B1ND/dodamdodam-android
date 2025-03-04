package com.b1nd.dodam.register.api

import com.b1nd.dodam.network.core.DodamUrl
import com.b1nd.dodam.network.core.model.DefaultResponse
import com.b1nd.dodam.network.core.util.defaultSafeRequest
import com.b1nd.dodam.register.datasource.RegisterDataSource
import com.b1nd.dodam.register.model.ChildrenRequest
import com.b1nd.dodam.register.model.RegisterParentRequest
import com.b1nd.dodam.register.model.RegisterRequest
import com.b1nd.dodam.register.model.RegisterTeacherRequest
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType

internal class RegisterService(
    private val client: HttpClient,
) : RegisterDataSource {

    override suspend fun register(email: String, grade: Int, id: String, name: String, number: Int, phone: String, pw: String, room: Int) {
        return defaultSafeRequest {
            client.post(DodamUrl.Member.REGISTER) {
                contentType(ContentType.Application.Json)
                setBody(
                    RegisterRequest(
                        email = email,
                        grade = grade,
                        id = id,
                        name = name,
                        number = number,
                        phone = phone,
                        pw = pw,
                        room = room,
                    ),
                )
            }.body<DefaultResponse>()
        }
    }

    override suspend fun registerTeacher(id: String, email: String, name: String, phone: String, pw: String, position: String, tel: String) =
        defaultSafeRequest {
            client.post(DodamUrl.Member.REGISTER_TEACHER) {
                contentType(ContentType.Application.Json)
                setBody(
                    RegisterTeacherRequest(
                        id = id,
                        email = email,
                        name = name,
                        phone = phone,
                        pw = pw,
                        position = position,
                        tel = tel,
                    ),
                )
            }.body<DefaultResponse>()
        }

    override suspend fun registerParent(id: String, pw: String, name: String, childrenList: List<ChildrenRequest>, phone: String) {
        defaultSafeRequest {
            client.post(DodamUrl.Member.REGISTER_PARENT) {
                setBody(
                    RegisterParentRequest(
                        id = id,
                        pw = pw,
                        name = name,
                        relationInfo = childrenList,
                        phone = phone,
                    ),
                )
            }.body<DefaultResponse>()
        }
    }
}
