package com.b1nd.dodam.core

import com.b1nd.dodam.common.exception.BadRequestException
import com.b1nd.dodam.common.exception.ConflictException
import com.b1nd.dodam.common.exception.ForbiddenException
import com.b1nd.dodam.common.exception.InternalServerException
import com.b1nd.dodam.common.exception.LockedException
import com.b1nd.dodam.common.exception.NotFoundException
import com.b1nd.dodam.common.exception.TooManyRequestsException
import com.b1nd.dodam.common.exception.UnauthorizedException
import com.b1nd.dodam.network.core.model.DefaultResponse
import com.b1nd.dodam.network.core.model.Response
import com.b1nd.dodam.network.core.util.defaultSafeRequest
import com.b1nd.dodam.network.core.util.safeRequest
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class DefaultSafeRequestTest {


    private val testDispatcher = StandardTestDispatcher()

    @Test
    fun 성공적인_요청_테스트() = runTest(testDispatcher) {
        val mockResponse = DefaultResponse(
            status = 200,
            message = "OK"
        )

        val result = defaultSafeRequest { mockResponse }
        assertEquals(Unit, result)
    }

    @Test
    fun 베드리퀘스트_에러_테스트() = runTest(testDispatcher) {
        val mockResponse = DefaultResponse(
            status = 400,
            message = "Bad Request"
        )
        assertFailsWith<BadRequestException> {
            defaultSafeRequest { mockResponse }
        }
    }

    @Test
    fun 인증_에러_테스트() = runTest(testDispatcher) {
        val mockResponse = DefaultResponse(
            status = 401,
            message = "Unauthorized"
        )
        assertFailsWith<UnauthorizedException> {
            defaultSafeRequest { mockResponse }
        }
    }

    @Test
    fun 금지된_요청_테스트() = runTest(testDispatcher) {
        val mockResponse = DefaultResponse(
            status = 403,
            message = "Forbidden"
        )
        assertFailsWith<ForbiddenException> {
            defaultSafeRequest { mockResponse }
        }
    }

    @Test
    fun 찾을_수_없음_테스트() = runTest(testDispatcher) {
        val mockResponse = DefaultResponse(
            status = 404,
            message = "Not Found"
        )
        assertFailsWith<NotFoundException> {
            defaultSafeRequest { mockResponse }
        }
    }

    @Test
    fun 충돌_에러_테스트() = runTest(testDispatcher) {
        val mockResponse = DefaultResponse(
            status = 409,
            message = "Conflict"
        )
        assertFailsWith<ConflictException> {
            defaultSafeRequest { mockResponse }
        }
    }

    @Test
    fun 잠금_에러_테스트() = runTest(testDispatcher) {
        val mockResponse = DefaultResponse(
            status = 423,
            message = "Locked"
        )
        assertFailsWith<LockedException> {
            defaultSafeRequest { mockResponse }
        }
    }

    @Test
    fun 너무_많은_요청_테스트() = runTest(testDispatcher) {
        val mockResponse = DefaultResponse(
            status = 429,
            message = "Too Many Requests"
        )
        assertFailsWith<TooManyRequestsException> {
            defaultSafeRequest { mockResponse }
        }
    }

    @Test
    fun 내부_서버_에러_테스트() = runTest(testDispatcher) {
        val mockResponse = DefaultResponse(
            status = 500,
            message = "Internal Server Error"
        )
        assertFailsWith<InternalServerException> {
            defaultSafeRequest { mockResponse }
        }
    }




}