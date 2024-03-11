package com.b1nd.dodam.register.api

import com.b1nd.dodam.network.core.DodamUrl
import com.b1nd.dodam.network.core.model.DefaultResponse
import com.b1nd.dodam.network.core.util.defaultSafeRequest
import com.b1nd.dodam.register.datasource.RegisterDataSource
import com.b1nd.dodam.register.model.RegisterRequest
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import javax.inject.Inject

internal class RegisterService @Inject constructor(
    private val client: HttpClient,
) : RegisterDataSource {

    override suspend fun register(email: String, grade: Int, id: String, name: String, number: Int, phone: String, pw: String, room: Int) {
        return defaultSafeRequest {
            client.post(DodamUrl.Member.REGISTER) {
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
}
